package com.mobiera.lib.etsi102222.model;

import java.io.IOException;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.FCPTemplate;
import com.mobiera.lib.etsi102221.model.UICCCommand;

public class ResizeFileCommand extends UICCCommand {

	
	protected FCPTemplate fcpTemplate;

	@Override
	protected int getInstructionCode() {
		return 0xD4;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("-----------");
		sb.append("\nDELETE FILE");
		sb.append("\n * FCP Template: " + fcpTemplate.toString());
		sb.append("\n-----------");
		return sb.toString();
		
	}
	
	public static class Builder {

		protected FCPTemplate fcpTemplate;
		
		public Builder() {
		}
		
		public Builder fcpTemplate(FCPTemplate value) {
			this.fcpTemplate = value;
			return this;
		}

		public ResizeFileCommand build() throws Etsi102222Exception, Etsi102221Exception, IOException {
			
			if (fcpTemplate == null)
				throw new Etsi102222Exception("Missing FCP Template");
			
			// Mandatory TLVs (all cases)
			if (fcpTemplate.getFileIdentifier() == null)
				throw new Etsi102222Exception("Missing File Identifier");
			
			// Mandatory TLVs for DF
			if (fcpTemplate.getTotalFileSize() == null &&
						fcpTemplate.getFileSize() == null)
						throw new Etsi102222Exception("Missing Total File Size or File Size TLV");

			if (fcpTemplate.getTotalFileSize() != null &&
					fcpTemplate.getFileSize() != null)
					throw new Etsi102222Exception("FCP shall include either Total File Size or File Size TLV");
			
			ResizeFileCommand output = new ResizeFileCommand();
			output.fcpTemplate = this.fcpTemplate;
			
			output.data = this.fcpTemplate.getBytes();
			return output;
		}
	}


}
