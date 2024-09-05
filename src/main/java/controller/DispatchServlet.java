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
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static controller.URLPath.*;

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

    private void doHandler(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        String contextPath = getServletContext().getContextPath();
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String method = request.getMethod();

        // Debugging: Log servletPath and pathInfo
        System.out.println("servletPath: " + servletPath);
        System.out.println("pathInfo: " + pathInfo);

        System.out.print(servletPath + ", " + pathInfo + ", " + method + ", 參數:");
        request.getParameterMap().entrySet().forEach((e) -> System.out.print(e.getKey() + ":" + Arrays.toString(e.getValue()) + ", "));
        System.out.println();

        HttpSession session = request.getSession();

        String jspLocation = "";  // 初始化目標頁面的變數

        // 調整 switch 的邏輯，直接判斷 servletPath
        switch (servletPath) {
        case "/servlet/DispatchServlet": 
            String action = request.getParameter("action");
            System.out.println("Action: " + action);  // 日誌：顯示 action 的值

            // 如果 action 為 null，直接根據 POST 方法判斷
            if (action == null) {
                if (method.equals("POST")) { 
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    // 進行登入邏輯處理
                    Optional<User> userOpt = dao.findUserByUsername(username);
                    if (userOpt.isEmpty() || !password.equals(userOpt.get().getPassword())) {
                        System.out.println("無此使用者或密碼不正確");
                        jspLocation = "/WEB-INF/groupbuy/error.jsp";
                        return;
                    }
                    System.out.println("使用者登入成功");
                    session.setAttribute("user", userOpt.get());
                    List<Product> products = dao.findAllProducts();
                    System.out.println("商品資訊: " + products);
                    request.setAttribute("products", products);
                    jspLocation = "/WEB-INF/groupbuy/frontend/main.jsp";  // 設定登入成功後的首頁
                } else {
                    // 如果不是 POST 請求，也可以設置其他預設行為
                    System.out.println("非 POST 請求，進入預設行為");
                    jspLocation = "/WEB-INF/views/預設頁面.jsp"; 
                }
            } else if ("login".equals(action)) {  // 處理 login 的情況
                if (method.equals("POST")) { 
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    Optional<User> userOpt = dao.findUserByUsername(username);
                    if (userOpt.isEmpty() || !password.equals(userOpt.get().getPassword())) {
                        System.out.println("無此使用者或密碼不正確");
                        jspLocation = "/WEB-INF/error.jsp";
                        
                        RequestDispatcher rd = request.getRequestDispatcher(jspLocation);
                        rd.forward(request, response);

                        return;
                    }else if(userOpt.get().getLevel() == 1) {
                    	jspLocation = "/WEB-INF/groupbuy/frontend/main.jsp";// 設定正確的 JSP 路徑
                    }else if(userOpt.get().getLevel() == 2) {
                    	jspLocation = "/WEB-INF/groupbuy/backend/main.jsp";
                    }else {
                    	 System.out.println("無權限");
                         jspLocation = "/WEB-INF/error.jsp";
                         
                         RequestDispatcher rd = request.getRequestDispatcher(jspLocation);
                         rd.forward(request, response);

                         return;
                    }
                    System.out.println("使用者登入成功");
                    session.setAttribute("user", userOpt.get());
                }
                List<Product> products = dao.findAllProducts();
                System.out.println("商品資訊: " + products);
                request.setAttribute("products", products);
                
            } else if ("cart".equals(action)) {  // 處理購物車的情況
                jspLocation = "/WEB-INF/views/購物車頁.jsp";
            } else if ("result".equals(action)) {  // 處理結帳成功的情況
            	System.out.println("處理 result 的情況");
                jspLocation = "/WEB-INF/groupbuy/frontend/result.jsp";
            } else {  // 預設處理，當 action 未識別或為 null 時
                System.out.println("未識別的 action: " + action + "，使用預設處理。");
                jspLocation = "/WEB-INF/views/預設頁面2.jsp";  // 設置預設頁面
            }
            break;

        // 其他 case 分支未變更
    

            case "/groupbuy/login.jsp": 
                // 對應到前台登入頁面
                jspLocation = "/WEB-INF/groupbuy/frontend/login.jsp"; 
                break;
                
            case "/groupbuy/frontend/result.jsp": 
                jspLocation = "/WEB-INF/groupbuy/frontend/result.jsp";  
                break;

            case "/groupbuy/frontend/cart.jsp": 
                jspLocation = "/WEB-INF/groupbuy/frontend/cart.jsp";  
                break;

            case "/groupbuy/frontend/finish.jsp": 
                jspLocation = "/WEB-INF/groupbuy/frontend/finish.jsp"; 
                break;

            case "/groupbuy/backend/main.jsp": 
                jspLocation = "/WEB-INF/groupbuy/backend/main.jsp"; 
                break;

            case "/groupbuy/backend/result.jsp": 
                jspLocation = "/WEB-INF/groupbuy/backend/result.jsp"; 
                break;

            case "/groupbuy/backend/report.jsp": 
                jspLocation = "/WEB-INF/groupbuy/backend/report.jsp"; 
                break;

            default:
                // 處理無匹配路徑的情況
                System.out.println("無效的請求路徑: " + servletPath);
                response.getWriter().print("無效的請求路徑");
                return;
        }

        // 檢查是否有設定轉發的 JSP 路徑
        if (jspLocation.isEmpty()) {
            System.out.println("JSP 路徑未設定，請檢查 switch 內部的路徑配置。");
            response.getWriter().print("JSP 路徑未設定");
            
            return;
        }

        // 打印轉發的路徑，幫助檢查是否正確
        System.out.println("Forwarding to: " + jspLocation);

        RequestDispatcher rd = request.getRequestDispatcher(jspLocation);
        if (rd == null) {
            System.out.println("RequestDispatcher is null for path: " + jspLocation);
        }
        rd.forward(request, response);
    }
}
