/**
 * 
 */
/**
 *比较两个数组是否相等
 * @param arr1
 * @param arr2
 * @returns
 */
function arrayEqual(arr1,arr2){
	if(arr1==arr2)return true;
	if(arr1.length!=arr2.length)return false;
	for(var i=0;i<arr1.length;i++){
		if(arr1[i]!=arr2[i])return false;
	}
	return true;
}
/**
 * 为元素添加class
 */
var hasClass=require("./hasClass");
function addClass(ele,cls){
	if(!hasClass(ele,cls)){
		ele.className+=' '+cls;
	}
}
/**
 * 判断是否含有某个class
 * @param ele
 * @param cls
 * @returns
 */
function hasClass(ele,cls){
	return (new RegExp('(\\s|^)'+cls+'(\\s|$)')).test(ele.className);
}
