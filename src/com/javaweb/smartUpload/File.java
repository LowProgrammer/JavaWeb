package com.javaweb.smartUpload;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
public class File {
	private SmartUpload m_parent;
	private int m_startData = 0;
	private int m_endData = 0;
	private int m_size = 0;
	private String m_fieldname = new String();
	private String m_filename = new String();
	private String m_fileExt = new String();
	private String m_filePathName = new String();
	private String m_contentType = new String();
	private String m_contentDisp = new String();
	private String m_typeMime = new String();
	private String m_subTypeMime = new String();
	private String m_contentString = new String();
	private boolean m_isMissing = true;
	public static final int SAVEAS_AUTO = 0;
	public static final int SAVEAS_VIRTUAL = 1;
	public static final int SAVEAS_PHYSICAL = 2;

	public void saveAs(String arg0) throws IOException, SmartUploadException {
		this.saveAs(arg0, 0);
	}

	public void saveAs(String arg0, int arg1) throws IOException, SmartUploadException {
		new String();
		String arg2 = this.m_parent.getPhysicalPath(arg0, arg1);
		if (arg2 == null) {
			throw new IllegalArgumentException("There is no specified destination file (1140).");
		} else {
			try {
				java.io.File arg3 = new java.io.File(arg2);
				FileOutputStream arg4 = new FileOutputStream(arg3);
				arg4.write(this.m_parent.m_binArray, this.m_startData, this.m_size);
				arg4.close();
			} catch (IOException arg5) {
				throw new SmartUploadException("File can\'t be saved (1120).");
			}
		}
	}

	public void fileToField(ResultSet arg0, String arg1)
			throws ServletException, IOException, SmartUploadException, SQLException {
		long arg2 = 0L;
		int arg4 = 65536;
		boolean arg5 = false;
		int arg6 = this.m_startData;
		if (arg0 == null) {
			throw new IllegalArgumentException("The RecordSet cannot be null (1145).");
		} else if (arg1 == null) {
			throw new IllegalArgumentException("The columnName cannot be null (1150).");
		} else if (arg1.length() == 0) {
			throw new IllegalArgumentException("The columnName cannot be empty (1155).");
		} else {
			arg2 = BigInteger.valueOf((long) this.m_size).divide(BigInteger.valueOf((long) arg4)).longValue();
			int arg11 = BigInteger.valueOf((long) this.m_size).mod(BigInteger.valueOf((long) arg4)).intValue();

			try {
				for (int arg7 = 1; (long) arg7 < arg2; ++arg7) {
					arg0.updateBinaryStream(arg1, new ByteArrayInputStream(this.m_parent.m_binArray, arg6, arg4), arg4);
					arg6 = arg6 == 0 ? 1 : arg6;
					arg6 = arg7 * arg4 + this.m_startData;
				}

				if (arg11 > 0) {
					arg0.updateBinaryStream(arg1, new ByteArrayInputStream(this.m_parent.m_binArray, arg6, arg11),
							arg11);
				}
			} catch (SQLException arg9) {
				byte[] arg8 = new byte[this.m_size];
				System.arraycopy(this.m_parent.m_binArray, this.m_startData, arg8, 0, this.m_size);
				arg0.updateBytes(arg1, arg8);
			} catch (Exception arg10) {
				throw new SmartUploadException("Unable to save file in the DataBase (1130).");
			}

		}
	}

	public boolean isMissing() {
		return this.m_isMissing;
	}

	public String getFieldName() {
		return this.m_fieldname;
	}

	public String getFileName() {
		return this.m_filename;
	}

	public String getFilePathName() {
		return this.m_filePathName;
	}

	public String getFileExt() {
		return this.m_fileExt;
	}

	public String getContentType() {
		return this.m_contentType;
	}

	public String getContentDisp() {
		return this.m_contentDisp;
	}

	public String getContentString() {
		String arg0 = new String(this.m_parent.m_binArray, this.m_startData, this.m_size);
		return arg0;
	}

	public String getTypeMIME() throws IOException {
		return this.m_typeMime;
	}

	public String getSubTypeMIME() {
		return this.m_subTypeMime;
	}

	public int getSize() {
		return this.m_size;
	}

	protected int getStartData() {
		return this.m_startData;
	}

	protected int getEndData() {
		return this.m_endData;
	}

	protected void setParent(SmartUpload arg0) {
		this.m_parent = arg0;
	}

	protected void setStartData(int arg0) {
		this.m_startData = arg0;
	}

	protected void setEndData(int arg0) {
		this.m_endData = arg0;
	}

	protected void setSize(int arg0) {
		this.m_size = arg0;
	}

	protected void setIsMissing(boolean arg0) {
		this.m_isMissing = arg0;
	}

	protected void setFieldName(String arg0) {
		this.m_fieldname = arg0;
	}

	protected void setFileName(String arg0) {
		this.m_filename = arg0;
	}

	protected void setFilePathName(String arg0) {
		this.m_filePathName = arg0;
	}

	protected void setFileExt(String arg0) {
		this.m_fileExt = arg0;
	}

	protected void setContentType(String arg0) {
		this.m_contentType = arg0;
	}

	protected void setContentDisp(String arg0) {
		this.m_contentDisp = arg0;
	}

	protected void setTypeMIME(String arg0) {
		this.m_typeMime = arg0;
	}

	protected void setSubTypeMIME(String arg0) {
		this.m_subTypeMime = arg0;
	}

	public byte getBinaryData(int arg0) {
		if (this.m_startData + arg0 > this.m_endData) {
			throw new ArrayIndexOutOfBoundsException("Index Out of range (1115).");
		} else {
			return this.m_startData + arg0 <= this.m_endData ? this.m_parent.m_binArray[this.m_startData + arg0] : 0;
		}
	}
}
