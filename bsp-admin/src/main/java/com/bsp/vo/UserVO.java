package com.bsp.vo;

import com.bsp.entity.User;
import com.bsp.entity.UserInfor;

public class UserVO {

	private User user;
	private UserInfor details;
	
	public UserVO(User user, UserInfor details) {
		super();
		this.user = user;
		this.details = details;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserInfor getDetails() {
		return details;
	}

	public void setDetails(UserInfor details) {
		this.details = details;
	}

}
