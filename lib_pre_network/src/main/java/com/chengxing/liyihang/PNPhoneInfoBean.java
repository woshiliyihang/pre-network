package com.chengxing.liyihang;

public class PNPhoneInfoBean extends PNBean {


    //公共
    public int pId;
    public boolean isShowClassTitle=false;//是否显示分类栏目名称
    public boolean isClassEndItem=false;//分组最后一个item
    public String headImg;
    public String nickname;
    public String nikeName;
    public boolean isSelect=false;
    public int itemType=0;//1 加成员组  2 删除成员组

    //本地通讯录字段
    public String name;
    public String number;
    public String sortKey;
    public int id;
    public String headerImgPath;


    //获取app 用户手机资料
    public String username;
    public String mobile;
    public int isFriend;//0 不是 1是的

    //用户我的好友数据结构
    public String initials;

    //用户好友验证数据
    public int friendId;
    public String remark;
    public int state;


    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isClassEndItem() {
        return isClassEndItem;
    }

    public void setClassEndItem(boolean classEndItem) {
        isClassEndItem = classEndItem;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public boolean isShowClassTitle() {
        return isShowClassTitle;
    }

    public void setShowClassTitle(boolean showClassTitle) {
        isShowClassTitle = showClassTitle;
    }

    public String getNickname() {
        if (nickname!=null)
            return nickname;
        if (nikeName!=null)
            return nikeName;
        return null;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public String getHeaderImgPath() {
        return headerImgPath;
    }

    public void setHeaderImgPath(String headerImgPath) {
        this.headerImgPath = headerImgPath;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSortKey() {
        if (sortKey==null)
            sortKey=initials;
        if (sortKey==null)
        {
            sortKey="#";
        }
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
