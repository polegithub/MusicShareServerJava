package eric.clapton.musician.service.order;

public class OrderErrors {
	public static final int NO_ACCOUNT_EXISTS = 0x3001_0001;
	public static final int NO_ORDER_EXISTS = 0x5001_0002;
	public static final int ORDER_NOT_UPDATABLE = 0x5001_0003;
	public static final int ORDER_EDIT_NO_ADDRESS = 0x5001_2003;
	public static final int ORDER_EDIT_NO_DEADLINE = 0x5001_2004;

	public static final int ORDER_NOT_DELETABLE = 0x5001_3000;
}
