package entity;

public class User {
	private Integer userId; //使用者ID
	private String username; //使用者名稱
	private String password; //使用者密碼
	private int level; //使用者權限
	
	public User() {}
	
	public User(Integer userId, String username, String password, int level) {
		super();
		userId = userId;
		this.username = username;
		this.password = password;
		this.level = level;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + "username=" + username + 
				"password=" + password + "level=" + level;
	}
}
