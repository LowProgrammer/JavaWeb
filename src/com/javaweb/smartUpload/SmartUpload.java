package com.javaweb.smartUpload;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class SmartUpload {
	protected byte[] m_binArray;
	protected HttpServletRequest m_request;
	protected HttpServletResponse m_response;
	protected ServletContext m_application;
	private int m_totalBytes = 0;
	private int m_currentIndex = 0;
	private int m_startData = 0;
	private int m_endData = 0;
	private String m_boundary = new String();
	private long m_totalMaxFileSize = 0L;
	private long m_maxFileSize = 0L;
	private Vector m_deniedFilesList = new Vector();
	private Vector m_allowedFilesList = new Vector();
	private boolean m_denyPhysicalPath = false;
	private boolean m_forcePhysicalPath = false;
	private String m_contentDisposition = new String();
	public static final int SAVE_AUTO = 0;
	public static final int SAVE_VIRTUAL = 1;
	public static final int SAVE_PHYSICAL = 2;
	private Files m_files = new Files();
	private Request m_formRequest = new Request();

	public final void init(ServletConfig arg0) throws ServletException {
		this.m_application = arg0.getServletContext();
	}

	public void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		this.m_request = arg0;
		this.m_response = arg1;
	}

	public final void initialize(ServletConfig arg0, HttpServletRequest arg1, HttpServletResponse arg2)
			throws ServletException, UnsupportedEncodingException {
		this.m_application = arg0.getServletContext();
		this.m_request = arg1;
		this.m_response = arg2;
	}

	public final void initialize(PageContext arg0) throws ServletException {
		this.m_application = arg0.getServletContext();
		this.m_request = (HttpServletRequest) arg0.getRequest();
		this.m_response = (HttpServletResponse) arg0.getResponse();
	}

	public final void initialize(ServletContext arg0, HttpSession arg1, HttpServletRequest arg2,
			HttpServletResponse arg3, JspWriter arg4) throws ServletException {
		this.m_application = arg0;
		this.m_request = arg2;
		this.m_response = arg3;
	}

	public void upload() throws ServletException, IOException, SmartUploadException {
		int arg0 = 0;
		boolean arg1 = false;
		long arg2 = 0L;
		boolean arg4 = false;
		new String();
		new String();
		String arg7 = new String();
		String arg8 = new String();
		String arg9 = new String();
		String arg10 = new String();
		String arg11 = new String();
		String arg12 = new String();
		String arg13 = new String();
		boolean arg14 = false;
		this.m_totalBytes = this.m_request.getContentLength();

		int arg17;
		for (this.m_binArray = new byte[this.m_totalBytes]; arg0 < this.m_totalBytes; arg0 += arg17) {
			try {
				this.m_request.getInputStream();
				arg17 = this.m_request.getInputStream().read(this.m_binArray, arg0, this.m_totalBytes - arg0);
			} catch (Exception arg16) {
				throw new SmartUploadException("Unable to upload.");
			}
		}

		for (; !arg4 && this.m_currentIndex < this.m_totalBytes; ++this.m_currentIndex) {
			if (this.m_binArray[this.m_currentIndex] == 13) {
				arg4 = true;
			} else {
				this.m_boundary = this.m_boundary + (char) this.m_binArray[this.m_currentIndex];
			}
		}

		if (this.m_currentIndex != 1) {
			++this.m_currentIndex;

			while (this.m_currentIndex < this.m_totalBytes) {
				String arg5 = this.getDataHeader();
				this.m_currentIndex += 2;
				arg14 = arg5.indexOf("filename") > 0;
				String arg6 = this.getDataFieldValue(arg5, "name");
				if (arg14) {
					arg9 = this.getDataFieldValue(arg5, "filename");
					arg7 = this.getFileName(arg9);
					arg8 = this.getFileExt(arg7);
					arg10 = this.getContentType(arg5);
					arg11 = this.getContentDisp(arg5);
					arg12 = this.getTypeMIME(arg10);
					arg13 = this.getSubTypeMIME(arg10);
				}

				this.getDataSection();
				if (arg14 && arg7.length() > 0) {
					if (this.m_deniedFilesList.contains(arg8)) {
						throw new SecurityException("The extension of the file is denied to be uploaded (1015).");
					}

					if (!this.m_allowedFilesList.isEmpty() && !this.m_allowedFilesList.contains(arg8)) {
						throw new SecurityException("The extension of the file is not allowed to be uploaded (1010).");
					}

					if (this.m_maxFileSize > 0L
							&& (long) (this.m_endData - this.m_startData + 1) > this.m_maxFileSize) {
						throw new SecurityException("Size exceeded for this file : " + arg7 + " (1105).");
					}

					arg2 += (long) (this.m_endData - this.m_startData + 1);
					if (this.m_totalMaxFileSize > 0L && arg2 > this.m_totalMaxFileSize) {
						throw new SecurityException("Total File Size exceeded (1110).");
					}
				}

				if (arg14) {
					File arg15 = new File();
					arg15.setParent(this);
					arg15.setFieldName(arg6);
					arg15.setFileName(arg7);
					arg15.setFileExt(arg8);
					arg15.setFilePathName(arg9);
					arg15.setIsMissing(arg9.length() == 0);
					arg15.setContentType(arg10);
					arg15.setContentDisp(arg11);
					arg15.setTypeMIME(arg12);
					arg15.setSubTypeMIME(arg13);
					if (arg10.indexOf("application/x-macbinary") > 0) {
						this.m_startData += 128;
					}

					arg15.setSize(this.m_endData - this.m_startData + 1);
					arg15.setStartData(this.m_startData);
					arg15.setEndData(this.m_endData);
					this.m_files.addFile(arg15);
				} else {
					String arg18 = new String(this.m_binArray, this.m_startData, this.m_endData - this.m_startData + 1,"utf-8");
					this.m_formRequest.putParameter(arg6, arg18);
				}

				if ((char) this.m_binArray[this.m_currentIndex + 1] == 45) {
					break;
				}

				this.m_currentIndex += 2;
			}

		}
	}

	public int save(String arg0) throws ServletException, IOException, SmartUploadException {
		return this.save(arg0, 0);
	}

	public int save(String arg0, int arg1) throws ServletException, IOException, SmartUploadException {
		int arg2 = 0;
		if (arg0 == null) {
			arg0 = this.m_application.getRealPath("/");
		}

		if (arg0.indexOf("/") != -1) {
			if (arg0.charAt(arg0.length() - 1) != 47) {
				arg0 = arg0 + "/";
			}
		} else if (arg0.charAt(arg0.length() - 1) != 92) {
			arg0 = arg0 + "\\";
		}

		for (int arg3 = 0; arg3 < this.m_files.getCount(); ++arg3) {
			if (!this.m_files.getFile(arg3).isMissing()) {
				this.m_files.getFile(arg3).saveAs(arg0 + this.m_files.getFile(arg3).getFileName(), arg1);
				++arg2;
			}
		}

		return arg2;
	}

	public int getSize() {
		return this.m_totalBytes;
	}

	public byte getBinaryData(int arg0) {
		try {
			byte arg1 = this.m_binArray[arg0];
			return arg1;
		} catch (Exception arg3) {
			throw new ArrayIndexOutOfBoundsException("Index out of range (1005).");
		}
	}

	public Files getFiles() {
		return this.m_files;
	}

	public Request getRequest() {
		return this.m_formRequest;
	}

	public void downloadFile(String arg0) throws ServletException, IOException, SmartUploadException {
		this.downloadFile(arg0, (String) null, (String) null);
	}

	public void downloadFile(String arg0, String arg1)
			throws ServletException, IOException, SmartUploadException, SmartUploadException {
		this.downloadFile(arg0, arg1, (String) null);
	}

	public void downloadFile(String arg0, String arg1, String arg2)
			throws ServletException, IOException, SmartUploadException {
		this.downloadFile(arg0, arg1, arg2, '﷨');
	}

	public void downloadFile(String arg0, String arg1, String arg2, int arg3)
			throws ServletException, IOException, SmartUploadException {
		if (arg0 == null) {
			throw new IllegalArgumentException("File \'" + arg0 + "\' not found (1040).");
		} else if (arg0.equals("")) {
			throw new IllegalArgumentException("File \'" + arg0 + "\' not found (1040).");
		} else if (!this.isVirtual(arg0) && this.m_denyPhysicalPath) {
			throw new SecurityException("Physical path is denied (1035).");
		} else {
			if (this.isVirtual(arg0)) {
				arg0 = this.m_application.getRealPath(arg0);
			}

			java.io.File arg4 = new java.io.File(arg0);
			FileInputStream arg5 = new FileInputStream(arg4);
			long arg6 = arg4.length();
			boolean arg8 = false;
			int arg9 = 0;
			byte[] arg10 = new byte[arg3];
			if (arg1 == null) {
				this.m_response.setContentType("application/x-msdownload");
			} else if (arg1.length() == 0) {
				this.m_response.setContentType("application/x-msdownload");
			} else {
				this.m_response.setContentType(arg1);
			}

			this.m_response.setContentLength((int) arg6);
			this.m_contentDisposition = this.m_contentDisposition == null ? "attachment;" : this.m_contentDisposition;
			if (arg2 == null) {
				this.m_response.setHeader("Content-Disposition",
						this.m_contentDisposition + " filename=" + this.getFileName(arg0));
			} else if (arg2.length() == 0) {
				this.m_response.setHeader("Content-Disposition", this.m_contentDisposition);
			} else {
				this.m_response.setHeader("Content-Disposition", this.m_contentDisposition + " filename=" + arg2);
			}

			while ((long) arg9 < arg6) {
				int arg11 = arg5.read(arg10, 0, arg3);
				arg9 += arg11;
				this.m_response.getOutputStream().write(arg10, 0, arg11);
			}

			arg5.close();
		}
	}

	public void downloadField(ResultSet arg0, String arg1, String arg2, String arg3)
			throws ServletException, IOException, SQLException {
		if (arg0 == null) {
			throw new IllegalArgumentException("The RecordSet cannot be null (1045).");
		} else if (arg1 == null) {
			throw new IllegalArgumentException("The columnName cannot be null (1050).");
		} else if (arg1.length() == 0) {
			throw new IllegalArgumentException("The columnName cannot be empty (1055).");
		} else {
			byte[] arg4 = arg0.getBytes(arg1);
			if (arg2 == null) {
				this.m_response.setContentType("application/x-msdownload");
			} else if (arg2.length() == 0) {
				this.m_response.setContentType("application/x-msdownload");
			} else {
				this.m_response.setContentType(arg2);
			}

			this.m_response.setContentLength(arg4.length);
			if (arg3 == null) {
				this.m_response.setHeader("Content-Disposition", "attachment;");
			} else if (arg3.length() == 0) {
				this.m_response.setHeader("Content-Disposition", "attachment;");
			} else {
				this.m_response.setHeader("Content-Disposition", "attachment; filename=" + arg3);
			}

			this.m_response.getOutputStream().write(arg4, 0, arg4.length);
		}
	}

	public void fieldToFile(ResultSet arg0, String arg1, String arg2)
			throws ServletException, IOException, SmartUploadException, SQLException {
		try {
			if (this.m_application.getRealPath(arg2) != null) {
				arg2 = this.m_application.getRealPath(arg2);
			}

			InputStream arg3 = arg0.getBinaryStream(arg1);
			FileOutputStream arg4 = new FileOutputStream(arg2);

			int arg5;
			while ((arg5 = arg3.read()) != -1) {
				arg4.write(arg5);
			}

			arg4.close();
		} catch (Exception arg6) {
			throw new SmartUploadException("Unable to save file from the DataBase (1020).");
		}
	}

	private String getDataFieldValue(String arg0, String arg1) {
		new String();
		String arg3 = new String();
		boolean arg4 = false;
		boolean arg5 = false;
		boolean arg6 = false;
		boolean arg7 = false;
		String arg2 = arg1 + "=" + '\"';
		int arg8 = arg0.indexOf(arg2);
		if (arg8 > 0) {
			int arg9 = arg8 + arg2.length();
			arg2 = "\"";
			int arg10 = arg0.indexOf(arg2, arg9);
			if (arg9 > 0 && arg10 > 0) {
				arg3 = arg0.substring(arg9, arg10);
			}
		}

		return arg3;
	}

	private String getFileExt(String arg0) {
		new String();
		boolean arg2 = false;
		boolean arg3 = false;
		if (arg0 == null) {
			return null;
		} else {
			int arg4 = arg0.lastIndexOf(46) + 1;
			int arg5 = arg0.length();
			String arg1 = arg0.substring(arg4, arg5);
			return arg0.lastIndexOf(46) > 0 ? arg1 : "";
		}
	}

	private String getContentType(String arg0) {
		new String();
		String arg2 = new String();
		boolean arg3 = false;
		boolean arg4 = false;
		String arg1 = "Content-Type:";
		int arg6 = arg0.indexOf(arg1) + arg1.length();
		if (arg6 != -1) {
			int arg5 = arg0.length();
			arg2 = arg0.substring(arg6, arg5);
		}

		return arg2;
	}

	private String getTypeMIME(String arg0) {
		new String();
		boolean arg2 = false;
		int arg3 = arg0.indexOf("/");
		return arg3 != -1 ? arg0.substring(1, arg3) : arg0;
	}

	private String getSubTypeMIME(String arg0) {
		new String();
		boolean arg2 = false;
		boolean arg3 = false;
		int arg4 = arg0.indexOf("/") + 1;
		if (arg4 != -1) {
			int arg5 = arg0.length();
			return arg0.substring(arg4, arg5);
		} else {
			return arg0;
		}
	}

	private String getContentDisp(String arg0) {
		new String();
		boolean arg2 = false;
		boolean arg3 = false;
		int arg4 = arg0.indexOf(":") + 1;
		int arg5 = arg0.indexOf(";");
		String arg1 = arg0.substring(arg4, arg5);
		return arg1;
	}

	private void getDataSection() {
		boolean arg0 = false;
		new String();
		int arg2 = this.m_currentIndex;
		int arg3 = 0;
		int arg4 = this.m_boundary.length();
		this.m_startData = this.m_currentIndex;
		this.m_endData = 0;

		while (arg2 < this.m_totalBytes) {
			if (this.m_binArray[arg2] == (byte) this.m_boundary.charAt(arg3)) {
				if (arg3 == arg4 - 1) {
					this.m_endData = arg2 - arg4 + 1 - 3;
					break;
				}

				++arg2;
				++arg3;
			} else {
				++arg2;
				arg3 = 0;
			}
		}

		this.m_currentIndex = this.m_endData + arg4 + 3;
	}

	private String getDataHeader() {
		int arg0 = this.m_currentIndex;
		int arg1 = 0;
		boolean arg2 = false;
		boolean arg3 = false;

		while (true) {
			while (!arg3) {
				if (this.m_binArray[this.m_currentIndex] == 13 && this.m_binArray[this.m_currentIndex + 2] == 13) {
					arg3 = true;
					arg1 = this.m_currentIndex - 1;
					this.m_currentIndex += 2;
				} else {
					++this.m_currentIndex;
				}
			}

			String arg4 = new String(this.m_binArray, arg0, arg1 - arg0 + 1);
			return arg4;
		}
	}

	private String getFileName(String arg0) {
		new String();
		new String();
		boolean arg3 = false;
		boolean arg4 = false;
		boolean arg5 = false;
		boolean arg6 = false;
		int arg7 = arg0.lastIndexOf(47);
		if (arg7 != -1) {
			return arg0.substring(arg7 + 1, arg0.length());
		} else {
			arg7 = arg0.lastIndexOf(92);
			return arg7 != -1 ? arg0.substring(arg7 + 1, arg0.length()) : arg0;
		}
	}

	public void setDeniedFilesList(String arg0) throws ServletException, IOException, SQLException {
		String arg1 = "";
		if (arg0 != null) {
			arg1 = "";

			for (int arg2 = 0; arg2 < arg0.length(); ++arg2) {
				if (arg0.charAt(arg2) == 44) {
					if (!this.m_deniedFilesList.contains(arg1)) {
						this.m_deniedFilesList.addElement(arg1);
					}

					arg1 = "";
				} else {
					arg1 = arg1 + arg0.charAt(arg2);
				}
			}

			if (arg1 != "") {
				this.m_deniedFilesList.addElement(arg1);
			}
		} else {
			this.m_deniedFilesList = null;
		}

	}

	public void setAllowedFilesList(String arg0) {
		String arg1 = "";
		if (arg0 != null) {
			arg1 = "";

			for (int arg2 = 0; arg2 < arg0.length(); ++arg2) {
				if (arg0.charAt(arg2) == 44) {
					if (!this.m_allowedFilesList.contains(arg1)) {
						this.m_allowedFilesList.addElement(arg1);
					}

					arg1 = "";
				} else {
					arg1 = arg1 + arg0.charAt(arg2);
				}
			}

			if (arg1 != "") {
				this.m_allowedFilesList.addElement(arg1);
			}
		} else {
			this.m_allowedFilesList = null;
		}

	}

	public void setDenyPhysicalPath(boolean arg0) {
		this.m_denyPhysicalPath = arg0;
	}

	public void setForcePhysicalPath(boolean arg0) {
		this.m_forcePhysicalPath = arg0;
	}

	public void setContentDisposition(String arg0) {
		this.m_contentDisposition = arg0;
	}

	public void setTotalMaxFileSize(long arg0) {
		this.m_totalMaxFileSize = arg0;
	}

	public void setMaxFileSize(long arg0) {
		this.m_maxFileSize = arg0;
	}

	protected String getPhysicalPath(String arg0, int arg1) throws IOException {
		String arg2 = new String();
		String arg3 = new String();
		new String();
		boolean arg5 = false;
		String arg4 = System.getProperty("file.separator");
		if (arg0 == null) {
			throw new IllegalArgumentException("There is no specified destination file (1140).");
		} else if (arg0.equals("")) {
			throw new IllegalArgumentException("There is no specified destination file (1140).");
		} else {
			if (arg0.lastIndexOf("\\") >= 0) {
				arg2 = arg0.substring(0, arg0.lastIndexOf("\\"));
				arg3 = arg0.substring(arg0.lastIndexOf("\\") + 1);
			}

			if (arg0.lastIndexOf("/") >= 0) {
				arg2 = arg0.substring(0, arg0.lastIndexOf("/"));
				arg3 = arg0.substring(arg0.lastIndexOf("/") + 1);
			}

			arg2 = arg2.length() == 0 ? "/" : arg2;
			java.io.File arg6 = new java.io.File(arg2);
			if (arg6.exists()) {
				arg5 = true;
			}

			if (arg1 == 0) {
				if (this.isVirtual(arg2)) {
					arg2 = this.m_application.getRealPath(arg2);
					if (arg2.endsWith(arg4)) {
						arg2 = arg2 + arg3;
					} else {
						arg2 = arg2 + arg4 + arg3;
					}

					return arg2;
				} else if (arg5) {
					if (this.m_denyPhysicalPath) {
						throw new IllegalArgumentException("Physical path is denied (1125).");
					} else {
						return arg0;
					}
				} else {
					throw new IllegalArgumentException("This path does not exist (1135).");
				}
			} else if (arg1 == 1) {
				if (this.isVirtual(arg2)) {
					arg2 = this.m_application.getRealPath(arg2);
					if (arg2.endsWith(arg4)) {
						arg2 = arg2 + arg3;
					} else {
						arg2 = arg2 + arg4 + arg3;
					}

					return arg2;
				} else if (arg5) {
					throw new IllegalArgumentException("The path is not a virtual path.");
				} else {
					throw new IllegalArgumentException("This path does not exist (1135).");
				}
			} else if (arg1 == 2) {
				if (arg5) {
					if (this.m_denyPhysicalPath) {
						throw new IllegalArgumentException("Physical path is denied (1125).");
					} else {
						return arg0;
					}
				} else if (this.isVirtual(arg2)) {
					throw new IllegalArgumentException("The path is not a physical path.");
				} else {
					throw new IllegalArgumentException("This path does not exist (1135).");
				}
			} else {
				return null;
			}
		}
	}

	public void uploadInFile(String arg0) throws IOException, SmartUploadException {
		boolean arg1 = false;
		int arg2 = 0;
		boolean arg3 = false;
		if (arg0 == null) {
			throw new IllegalArgumentException("There is no specified destination file (1025).");
		} else if (arg0.length() == 0) {
			throw new IllegalArgumentException("There is no specified destination file (1025).");
		} else if (!this.isVirtual(arg0) && this.m_denyPhysicalPath) {
			throw new SecurityException("Physical path is denied (1035).");
		} else {
			int arg8 = this.m_request.getContentLength();

			int arg9;
			for (this.m_binArray = new byte[arg8]; arg2 < arg8; arg2 += arg9) {
				try {
					arg9 = this.m_request.getInputStream().read(this.m_binArray, arg2, arg8 - arg2);
				} catch (Exception arg7) {
					throw new SmartUploadException("Unable to upload.");
				}
			}

			if (this.isVirtual(arg0)) {
				arg0 = this.m_application.getRealPath(arg0);
			}

			try {
				java.io.File arg4 = new java.io.File(arg0);
				FileOutputStream arg5 = new FileOutputStream(arg4);
				arg5.write(this.m_binArray);
				arg5.close();
			} catch (Exception arg6) {
				throw new SmartUploadException("The Form cannot be saved in the specified file (1030).");
			}
		}
	}

	private boolean isVirtual(String arg0) {
		if (this.m_application.getRealPath(arg0) != null) {
			java.io.File arg1 = new java.io.File(this.m_application.getRealPath(arg0));
			return arg1.exists();
		} else {
			return false;
		}
	}
}