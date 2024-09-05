package entity;

public class CartItem {
	private Integer itemId;//購物車明細ID
	private Integer cartId;//購物車ID
	private Integer productId;//商品ID
	private Integer quantity;//數量
	
	private Cart cart;
	private Product product;
	public CartItem() {
		super();
	}
	public CartItem(Integer itemId, Integer cartId, Integer productId, Integer quantity, Cart cart, Product product) {
		super();
		this.itemId = itemId;
		this.cartId = cartId;
		this.productId = productId;
		this.quantity = quantity;
		this.cart = cart;
		this.product = product;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getCartId() {
		return cartId;
	}
	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "CartItem [itemId=" + itemId + ", cartId=" + cartId + ", cart=" + cart + ", productId=" + productId
				+ ", product=" + product + ", quantity=" + quantity + "]";
	}
}
