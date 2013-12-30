package net.java.amateras.xlsbeans;

import java.util.List;

import net.java.amateras.xlsbeans.annotation.HorizontalRecords;
import net.java.amateras.xlsbeans.annotation.Sheet;

@Sheet(name = "Users")
public class UserList2 {
	private List<User> userList;

	public List<User> getUserList() {
		return userList;
	}

	@HorizontalRecords(tableLabel = "User Informations", recordClass = User.class)
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
