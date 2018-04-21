package com.github.takezoe.xlsbeans;

import com.github.takezoe.xlsbeans.annotation.*;

import java.util.List;

/**
 * @author Naoki Takezoe
 */
@Sheet(name = "NoSheet")
public class UserListNoSheet {

  private List<User> horizontalUsers;
  private List<User> verticalUsers;
  private String title;
  private String lastUpdate;
  private List<Unit> unitUsers;
  private String actionClassName;
  private String formClassName;
  private String labelSkip0;
  private String labelSkip1;

  public List<Unit> getUnitUsers() {
    return unitUsers;
  }

  @IterateTables(tableLabel = "部門情報", tableClass = Unit.class, bottom = 2)
  public void setUnitUsers(List<Unit> unitUsers) {
    this.unitUsers = unitUsers;
  }

  public List<User> getVerticalUsers() {
    return verticalUsers;
  }

  @VerticalRecords(tableLabel = "Vertical Table", recordClass = User.class)
  public void setVerticalUsers(List<User> verticalUsers) {
    this.verticalUsers = verticalUsers;
  }

  @HorizontalRecords(tableLabel = "User Informations", recordClass = User.class, terminateLabel = "Terminate")
  public void setHorizontalUsers(List<User> users) {
    this.horizontalUsers = users;
  }

  public List<User> getHorizontalUsers() {
    return this.horizontalUsers;
  }

  @LabelledCell(label = "Title", type = LabelledCellType.Right)
  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  @LabelledCell(label = "LastUpdated", type = LabelledCellType.Right)
  public void setLastUpdate(String lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public String getLastUpdate() {
    return this.lastUpdate;
  }

  public String getActionClassName() {
    return actionClassName;
  }

  @LabelledCell(label = "クラス名", headerLabel = "アクション", type = LabelledCellType.Right)
  public void setActionClassName(String actionClassName) {
    this.actionClassName = actionClassName;
  }

  public String getFormClassName() {
    return formClassName;
  }

  @LabelledCell(label = "クラス名", headerLabel = "アクションフォーム", type = LabelledCellType.Right)
  public void setFormClassName(String formClassName) {
    this.formClassName = formClassName;
  }

  public String getLabelSkip0() {
    return labelSkip0;
  }

  @LabelledCell(label = "離れたラベル", type = LabelledCellType.Right)
  public void setLabelSkip0(String labelSkip0) {
    this.labelSkip0 = labelSkip0;
  }

  public String getLabelSkip1() {
    return labelSkip1;
  }

  @LabelledCell(label = "離れたラベル", type = LabelledCellType.Right, skip = 1, range = 2)
  public void setLabelSkip1(String labelSkip1) {
    this.labelSkip1 = labelSkip1;
  }


}
