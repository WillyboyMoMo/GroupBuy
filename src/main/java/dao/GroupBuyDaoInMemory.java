package dao;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import entity.Cart;
import entity.CartItem;
import entity.Product;
import entity.User;

import static java.util.stream.Collectors.toList;

//In-Memory
public class GroupBuyDaoInMemory implements GroupBuyDao {
	
	// In-Memory List
	private static List<User> users = new CopyOnWriteArrayList<>();
	private static List<Product> products = new CopyOnWriteArrayList<>();
	private static List<Cart> carts = new CopyOnWriteArrayList<>();
	private static List<CartItem> cartItems = new CopyOnWriteArrayList<>();
	
	@Override
	public List<User> findAllUsers() {
		return users;
	}

	@Override
	public void addUser(User user) {
		users.add(user);
	}

	@Override
	public Boolean updateUserPassword(Integer userId, String newPassword) {
		Optional<User> userOpt = users.stream()
									  .filter(user -> user.getUserId().equals(userId))
									  .findAny();
		if(userOpt.isPresent()) {
			userOpt.get().setPassword(newPassword); // 找到就設定 password
			return true;
		}
		return false;
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		return users.stream().filter(user -> user.getUsername().equals(username)).findAny();
	}

	@Override
	public Optional<User> findUserById(Integer userId) {
		return users.stream().filter(user -> user.getUserId().equals(userId)).findAny();
	}

	@Override
	public List<Product> findAllProducts() {
		return products;
	}

	@Override
	public Optional<Product> findProductById(Integer productId) {
		return products.stream().filter(product -> product.getProductId().equals(productId)).findAny();
	}

	@Override
	public void addProduct(Product product) {
		products.add(product);
	}

	@Override
	public Boolean updateProductLaunch(Integer productId, Boolean isLaunch) {
		return products.stream()
					   .filter(product -> product.getProductId().equals(productId))
					   .peek(product -> product.setIsLaunch(isLaunch))
					   .findAny()
					   .isPresent();
	}

	@Override
	public void addCart(Cart cart) {
		carts.add(cart);
	}

	@Override
	public void addCartItem(CartItem cartItem) {
		cartItems.add(cartItem);
	}

	@Override
	public List<Cart> findAllCart() {
		return carts;
	}

	@Override
	public Optional<Cart> findCartById(Integer cartId) {
		return carts.stream().filter(cart -> cart.getCartId().intValue() == cartId.intValue()).findAny();
	}

	@Override
	public Optional<CartItem> findCartItemById(Integer itemId) {
		return cartItems.stream().filter(cartItem -> cartItem.getItemId().equals(itemId)).findAny();
	}

	@Override
	public List<Cart> findCartsByUserId(Integer userId) {
		return carts.stream().filter(cart -> cart.getUserId().equals(userId)).collect(toList());
	}

	@Override
	public List<Cart> findCartsbyUserIdAndCheckoutStatus(Integer userId, Boolean isCheckout) {
		/*
		return carts.stream()
					.filter(cart -> cart.getUserId().equals(userId))
					.filter(cart -> cart.getIsCheckout().equals(isCheckout))
					.collect(toList());
		*/			
		return carts.stream()
				.filter(cart -> cart.getUserId().equals(userId) && cart.getIsCheckout().equals(isCheckout))
				.collect(toList());
	}

	@Override
	public Optional<Cart> findNoneCheckoutCartByUserId(Integer userId) {
		// 尋找該使用者的尚未結帳購物車
		return carts.stream()
					.filter(cart -> cart.getUserId().equals(userId))
					.filter(cart -> cart.getIsCheckout() == null || !cart.getIsCheckout())
					.findAny();
	}

	@Override
	public Boolean checkoutCartByUserId(Integer userId) {
		// 要尋找該使用者的尚未結帳購物車, 才能進行結帳
		Optional<Cart> cartOpt = findNoneCheckoutCartByUserId(userId);
		if(cartOpt.isPresent()) {
			cartOpt.get().setIsCheckoutTime(true); // 結帳
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkoutCartById(Integer cartId) {
		return carts.stream()
				.filter(cart -> cart.getCartId().equals(cartId))
				.peek(cart -> cart.setIsCheckoutTime(true)) // 結帳
				.findAny().isPresent();
	}

	@Override
	public Boolean removeCartItemById(Integer cartItemId) {
		Optional<CartItem> cartItemOpt = cartItems.stream()
				.filter(cartItem -> cartItem.getItemId().equals(cartItemId))
				.findAny();
		if(cartItemOpt.isPresent()) {
			cartItems.remove(cartItemOpt.get()); // 移除
			return true;
		}
		return false;
	}

	@Override
	public Boolean updateCartItemQuantity(Integer cartItemId, Integer quantity) {
		return cartItems.stream()
						.filter(cartItem -> cartItem.getItemId().equals(cartItemId))
						.peek(cartItem -> cartItem.setQuantity(quantity))
						.findAny()
						.isPresent();
	}

	@Override
	public List<Map<String, Object>> calculateTotalAmountPerUser() {
		// Home work
		return null;
	}
}
