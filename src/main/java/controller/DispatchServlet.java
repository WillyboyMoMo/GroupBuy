package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dao.GroupBuyDao;
import dao.GroupBuyDaoMsSQL;
import entity.Cart;
import entity.CartItem;
import entity.Product;
import entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static controller.URLPath.團購首頁;
import static controller.URLPath.登入首頁;
import static controller.URLPath.新增完成頁;
import static controller.URLPath.購物車頁;
import static controller.URLPath.結帳成功;
import static controller.URLPath.後台首頁;
import static controller.URLPath.後台商品新增;
import static controller.URLPath.後台報表;


// 過濾路徑
@WebServlet(value = {"/group_buy/*"})
public class DispatchServlet extends HttpServlet {
	
	private GroupBuyDao dao = GroupBuyDaoMsSQL.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doHandler(req, resp);
	}

	private void doHandler(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String contextPath = getServletContext().getContextPath();
		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		String method = request.getMethod();
		
		System.out.print(servletPath + ", " + pathInfo + ", " + method + ", 參數:");
		request.getParameterMap().entrySet().forEach((e) -> System.out.print(e.getKey() + ":" + Arrays.toString(e.getValue()) + ", "));
		System.out.println();
		
		HttpSession session = request.getSession();
		
		switch (servletPath + pathInfo) {
			case 登入首頁: // 按下登入頁的"前台登入"按鈕或按下"團購首頁"連結
				if(method.equals("POST")) { // 按下登入頁的"前台登入"按鈕
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					// 找到使用者
					Optional<User> userOpt = dao.findUserByUsername(username);
					// 判斷是否有找到使用者與密碼判斷
					if(userOpt.isEmpty() || !password.equals(userOpt.get().getPassword())) {
						System.out.println("無此使用者或密碼不正確");
						response.sendRedirect(contextPath + 登入首頁);
						return;
					}
					System.out.println("使用者登入成功");
					// 將使用者物件存入到 session 中
					session.setAttribute("user", userOpt.get());
				} 
				
				List<Product> products = dao.findAllProducts();
				System.out.println("商品資訊: " + products);
				request.setAttribute("products", products); // 將商品資訊放入到 request-scope 的屬性中
				break;
			case 新增完成頁:
				if(method.equals("POST")) {
					String productId = request.getParameter("productId"); // 取得欲購買商品
					String quantity = request.getParameter("quantity"); // 取得欲購買數量
					// 取得已登入的使用者
					User user = (User)session.getAttribute("user");
					// 使用者購物車
					Cart cart = null;
					// 先找該 user 目前有沒有還未結帳的購物車
					Optional<Cart> cartOpt = dao.findNoneCheckoutCartByUserId(user.getUserId());
					if(cartOpt.isPresent()) {
						cart = cartOpt.get(); // 取得該使用者的購物車
					} else {
						cart = new Cart(); // 建立一個新的購物車
						cart.setUserId(user.getUserId());
						dao.addCart(cart); // 存放到資料表中
						
						// 新增之後馬上又要查詢建議可以下達一段 delay
						try {
							Thread.sleep(10);
						} catch (Exception e) {
						}
						
						// 再抓一次使用者的 cart, 目的是要得到 carId
						cart = dao.findNoneCheckoutCartByUserId(user.getUserId()).get();
					}
					// 建立購物項目
					CartItem cartItem = new CartItem();
					cartItem.setCartId(cart.getCartId());
					cartItem.setProductId(Integer.parseInt(productId));
					cartItem.setQuantity(Integer.parseInt(quantity));
					dao.addCartItem(cartItem);
					
					// 將商品物件與數量傳下去給 jsp
					request.setAttribute("product", dao.findProductById(Integer.parseInt(productId)).get());
					request.setAttribute("quantity", quantity);
				} else {
					response.getWriter().print("非法進入~");
					return;
				}
				break;
			case 購物車頁:
				// 取得已登入的使用者
				User user = (User)session.getAttribute("user");
				// 取得尚未結帳的購物車
				Optional<Cart> cartOpt = dao.findNoneCheckoutCartByUserId(user.getUserId());
				if(cartOpt.isPresent()) {
					
					// 刪除修改的行為
					String _method = request.getParameter("_method") + "";
					String itemId = request.getParameter("itemId") + "";
					String quantity = request.getParameter("quantity") + "";
					switch (_method) {
						case "Put": // 修改數量
							int qty = Integer.parseInt(quantity);
							if(qty <= 0) {
								dao.removeCartItemById(Integer.parseInt(itemId));
							} else {
								dao.updateCartItemQuantity(Integer.parseInt(itemId), qty);
							}
							// 重新再取得最新資料
							cartOpt = dao.findNoneCheckoutCartByUserId(user.getUserId());
							break;
						case "Delete": // 刪除項目
							dao.removeCartItemById(Integer.parseInt(itemId));
							// 重新再取得最新資料
							cartOpt = dao.findNoneCheckoutCartByUserId(user.getUserId());
							break;	
					}
					
					Cart cart = cartOpt.get();
					int total = cart.getCartItems().stream().mapToInt(item -> item.getQuantity() * item.getProduct().getPrice()).sum();
					request.setAttribute("cart", cart);
					request.setAttribute("total", total);
					
				}
				break;
			case 結帳成功:
				// 取得已登入的使用者
				user = (User)session.getAttribute("user");
				// 取得尚未結帳的購物車
				cartOpt = dao.findNoneCheckoutCartByUserId(user.getUserId());
				if(cartOpt.isPresent()) {
					Cart cart = cartOpt.get();
					int total = cart.getCartItems().stream().mapToInt(item -> item.getQuantity() * item.getProduct().getPrice()).sum();
					request.setAttribute("cart", cart);
					request.setAttribute("total", total);
					// 結帳
					dao.checkoutCartById(cart.getCartId());
				}
				break;
			
			case 後台首頁:
				if(method.equals("GET")) {
					// 上下架
					String _method = request.getParameter("_method") + "";
					String productId = request.getParameter("productId");
					String isLaunch = request.getParameter("isLaunch");
					
					switch(_method) {
						case "Put":
							if(productId != null && isLaunch != null) {
								dao.updateProductLaunch(Integer.parseInt(productId), Boolean.parseBoolean(isLaunch));
							}
						break;
					}
					
					// 取得所有商品
					List<Product> productList = dao.findAllProducts();
					request.setAttribute("products", productList);
				}
				break;
				
			case 後台商品新增:
				if(method.equals("POST")) {
					String productName = request.getParameter("productName");
					String productPrice = request.getParameter("productPrice");
					String productUnit = request.getParameter("productUnit");
					String isLaunch = request.getParameter("isLaunch");
					
					Product product = new Product();
					product.setProductName(productName);
					product.setPrice(Integer.parseInt(productPrice));
					product.setUnit(productUnit);
					product.setIsLaunch(isLaunch != null);
					dao.addProduct(product);
					
					request.setAttribute("product", product);
				}
				break;
			
			case 後台報表:
				if(method.equals("GET")) {
					// 揭露該 userId 的明細
					String userId = request.getParameter("userId");
					if(userId != null) {
						List<Cart> carts = dao.findCartsbyUserIdAndCheckoutStatus(Integer.parseInt(userId), true);
						//System.out.println(carts);
						request.setAttribute("carts", carts);
					}
					
					List<Map<String, Object>> reports = dao.calculateTotalAmountPerUser();
					request.setAttribute("reports", reports);
					
				}
				break;
				
		}
		
		// jsp 位置
		String jspLocation = "/WEB-INF/view" + servletPath + pathInfo;
		RequestDispatcher rd = request.getRequestDispatcher(jspLocation);
		rd.forward(request, response);
		
	}
	
}
