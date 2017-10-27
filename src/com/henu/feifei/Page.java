package com.henu.feifei;
/**
	*@ClassName:Page
	*@Description:TODO
	*@author:feifei
	*@date :2017年10月27日-下午12:50:17
	*@version:1.0
	*/
public class Page {
	private int pageSize=10;
	private int currentPage=1;
	private int totalPage=0;
	private int totalRows=0;
	private boolean hasBefore=false;
	private boolean hasNext=false;
	private String linkHTML="";
	private String pageURL;
	public int getTotalPage() {
		totalPage=((totalRows+pageSize)-1)/pageSize;
		return totalPage;
	}
	public void firstPage() {
		currentPage=1;
		this.setHasBefore(false);
		this.refresh();
	}
	public void beforePage() {
		currentPage--;
		this.refresh();
	}
	public void nextPage() {
		if(currentPage<totalPage) {
			currentPage++;
		}
		this.refresh();
	}
	public void lastPage() {
		currentPage=totalPage;
		this.setHasNext(false);
		this.refresh();
	}
	public void refresh() {
		if (totalPage<=1) {
			this.setHasBefore(false);
			this.setHasNext(false);
		} else if(currentPage==1){
			this.setHasBefore(false);
			this.setHasNext(true);
		}else if (currentPage==totalPage) {
			this.setHasBefore(true);
			this.setHasNext(false);
		}else {
			this.setHasBefore(true);
			this.setHasNext(true);
		}
	}
	public void setHasNext(boolean b) {
		hasNext=b;
	}
	public void setHasBefore(boolean b) {
		hasBefore=b;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public String getLinkHTML() {
		linkHTML+="一共有"+this.totalRows+"条记录";
		if(this.hasBefore) {
			linkHTML+="<a href='"+this.pageURL+"?currPage=1'>首页</a>";
			linkHTML+="&nbsp;&nbsp;&nbsp;&nbsp;";
			linkHTML+="<a href='"+this.pageURL+"?currPage="+this.currentPage+"&action=before'>上一页</a>";
			linkHTML+="&nbsp;&nbsp;&nbsp;&nbsp;";
		}else {
			linkHTML+="首页 &nbsp;&nbsp;&nbsp;&nbsp;上一页&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		if(this.hasNext) {
			linkHTML+="<a href='"+this.pageURL+"?currPage="+this.currentPage+"&action=next'>下一页</a>";
			linkHTML+="&nbsp;&nbsp;&nbsp;&nbsp;";
			linkHTML+="<a href='"+this.pageURL+"?currPage="+this.totalPage+"'>尾页</a>";
			linkHTML+="&nbsp;&nbsp;&nbsp;&nbsp;";
		}else {
			linkHTML+="下一页 &nbsp;&nbsp;&nbsp;&nbsp;尾页&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		linkHTML+="当前为"+this.currentPage+"/"+getTotalPage()+"页";
		return linkHTML;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isHasBefore() {
		return hasBefore;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setLinkHTML(String linkHTML) {
		this.linkHTML = linkHTML;
	}
	public String getPageURL() {
		return pageURL;
	}
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
}
