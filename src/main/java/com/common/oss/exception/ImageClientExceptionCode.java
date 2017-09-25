package com.common.oss.exception;

public class ImageClientExceptionCode {

	// 拒绝访问.
	public final static int AccessDenied = 0x0001;

	// 对象已经存在.
	public final static int ObjectAlreadyExists = 0x0002;

	// 对象太大.
	public final static int ObjectTooLarge = 0x0003;

	// 对象太小.
	public final static int ObjectToolSmall = 0x0004;

	// 无效参数.
	public final static int InvalidArgument = 0x0005;

	// 对象名无效.
	public final static int InvalidObjectName = 0x0006;

	// 内部错误.
	public final static int InternalError = 0x0007;

	// 缺少参数.
	public final static int MissingArgument = 0x0008;

	// 文件不存在.
	public final static int NoSuchObject = 0x0009;

	// 请求超时.
	public final static int RequestTimeout = 0x000A;

	// 文件夹数量过多.
	public final static int TooManyFolders = 0x000B;

	// 目录格式不对.
	public final static int InvalidDir = 0x000C;

	// 目录已经存在.
	public final static int DirAlreadyExits = 0x000D;

}
