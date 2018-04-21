package com.github.takezoe.xlsbeans;

import com.github.takezoe.xlsbeans.annotation.HorizontalRecords;
import com.github.takezoe.xlsbeans.annotation.Sheet;

import java.util.List;

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
