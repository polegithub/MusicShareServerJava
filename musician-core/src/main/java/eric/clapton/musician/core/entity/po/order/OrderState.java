package eric.clapton.musician.core.entity.po.order;

public enum OrderState {
	
	//音乐人状态								//商家状态
				
				
										/*
										 * 订单待付款/付款失败
										 */
										CREATED("已创建"), 
										
										/*
										 * 超时未付款
										 */
										CANCELLED("已取消"), 
										
										PAYING("付款"), 
										
	
	/*
	 * 报名中			
	 */
										// ...此处时间段小订单状态
										ING("抢购中"), 
										
										UNING("已过时"), 
	/*
	 * 报名完成
	 */
										ENSURE("待演出"), //商家确认 ,通知音乐人  //
										NORMAL("正常"), //该状态不被多时间段订单使用
										

										/*
										 * 确认完成演出
										 */									
										COMPLETE("演出完成"), 
										
										
	/*
	 * 待评价
	 */								 
										// ...此处时间段小订单状态
										EVALUATE("评价中"),
										
										
									/*
									 * 完成状态
									 */
									CLOSED("已结束");
	
	
	
	
	
	

	private final String description;

	OrderState(final String description) {
		this.description = description;
	}

	public final String getDescription() {
		return description;
	}
	
	
	public static String getStateMessage(String state){
		OrderState sta = OrderState.valueOf(state);
		return sta.getDescription();
	}
	
	public static void main(String[] args) {
		System.out.println(getStateMessage("CLOSED"));
	}
}
