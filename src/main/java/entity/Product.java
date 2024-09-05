package entity;

public class Product {
	private Integer productId; //商品ID
	private String productName; //商品名稱
	private Integer price; //商品價格
	private String unit; //單位
	private boolean isLaunch; //是否上架
	
	public Product(Integer productId, String productName, Integer price, String unit, boolean iaLaunch) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.unit = unit;
		this.isLaunch = isLaunch;
	}
	public Product() {
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public boolean getIsLaunch() {
		return isLaunch;
	}
	public void setIsLaunch(boolean isLaunch) {
		this.isLaunch = isLaunch;
	}
	@Override
	public String toString() {
		return "product [productId= " + productId + "productName= " + productName
				+ "price= " + price + "isLaunch" + isLaunch+ "]";
	}
	
	
}
