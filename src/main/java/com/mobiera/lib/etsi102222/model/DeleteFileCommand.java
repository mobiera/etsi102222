package com.mobiera.lib.etsi102222.model;

import com.mobiera.lib.etsi102221.model.UICCCommand;

public class DeleteFileCommand extends UICCCommand {

	
	protected Integer fid;
	
	@Override
	protected int getInstructionCode() {
		return 0xE4;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-----------");
		sb.append("\nDELETE FILE");
		sb.append("\n * File ID: 0x" + Integer.toHexString(fid).toUpperCase());
		sb.append("\n-----------");
		return sb.toString();
	}
	
	public static class Builder {

		protected Integer fileIdentifier;
		
		public Builder() {
		}

		public Builder fileIdentifier(int value) {
			this.fileIdentifier = value;
			return this;
		}

		public DeleteFileCommand build() throws Etsi102222Exception {
			DeleteFileCommand output = new DeleteFileCommand();
			output.fid = this.fileIdentifier;
			
			if (output.fid == null)
				throw new Etsi102222Exception("File ID cannot be null");
			
			output.p1 = 0;
			output.p2 = 0;
			output.data  = new byte [] { (byte) (output.fid >> 8), (byte) (output.fid & 0xFF) };
			
			return output;
		}
		
	}


}
