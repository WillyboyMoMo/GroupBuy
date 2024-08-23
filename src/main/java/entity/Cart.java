package entity;

import java.util.Date;
import java.util.List;

public class Cart {
	private Integer cartId; //購物車id
	private Integer userId; //使用者id
	private Boolean isCheckout; //是否結帳
	private Date checkoutTime; //結帳時間
	
	private User user;
	private List<CartItem> cartItems;
	
	public Cart(Integer cartId, Integer userId, Boolean isCheckout, Date checkoutTime, User user,
			List<CartItem> cartItems) {
		super();
		this.cartId = cartId;
		this.userId = userId;
		this.isCheckout = isCheckout;
		this.checkoutTime = checkoutTime;
		this.user = user;
		this.cartItems = cartItems;
	}

	public Cart() {
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Boolean getIsCheckout() {
		return isCheckout;
	}

	public void setIsCheckoutTime(Boolean isCheckoutTime) {
		this.isCheckout = isCheckoutTime;
	}

	public Date getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(Date checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	@Override
	public String toString() {
		return "Cart [CartId= " + cartId + "userId= " + userId
				+ "user= " + user + "cartItem= " + cartItems
				+ "isCheckout= " + isCheckout + "isCheckTime= " + checkoutTime + "]";
	}
}
