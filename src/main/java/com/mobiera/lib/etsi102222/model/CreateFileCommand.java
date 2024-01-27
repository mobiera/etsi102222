package com.mobiera.lib.etsi102222.model;

import java.io.IOException;

import com.mobiera.lib.etsi102221.Etsi102221Exception;
import com.mobiera.lib.etsi102221.model.FCPTemplate;
import com.mobiera.lib.etsi102221.model.UICCCommand;
import com.mobiera.lib.etsi102221.model.fcp.FileDescriptor.FileType;

public class CreateFileCommand extends UICCCommand {
	
	protected FCPTemplate fcpTemplate;

	@Override
	protected int getInstructionCode() {
		return 0xE0;
	}

	public static class Builder {

		protected FCPTemplate fcpTemplate;
		
		public Builder() {
		}
		
		public Builder fcpTemplate(FCPTemplate value) {
			this.fcpTemplate = value;
			return this;
		}

		public CreateFileCommand build() throws Etsi102222Exception, Etsi102221Exception, IOException {
			
			if (fcpTemplate == null)
				throw new Etsi102222Exception("Missing FCP Template");
			
			// Mandatory TLVs (all cases)
			if (fcpTemplate.getFileDescriptor() == null ||
					fcpTemplate.getFileIdentifier() == null ||
					fcpTemplate.getLifeCycleStatusInteger() == null ||
					fcpTemplate.getSecurityAttributes() == null)
				throw new Etsi102222Exception("Missing mandatory TLVs for file creation");
			
			// Mandatory TLVs for DF
			if (fcpTemplate.getFileDescriptor().getFileType() == FileType.DF_OR_ADF) {
				if (fcpTemplate.getTotalFileSize() == null ||
						fcpTemplate.getPinStatusTemplate() == null)
						throw new Etsi102222Exception("Missing mandatory TLVs for DF or ADF creation");
			} else
			// Mandatory TLVs for EF
			if (fcpTemplate.getFileDescriptor().getFileType() == FileType.DF_OR_ADF)
				if (fcpTemplate.getFileSize() == null)
					throw new Etsi102222Exception("Missing mandatory TLVs for EF creation");
			
			CreateFileCommand output = new CreateFileCommand();
			output.fcpTemplate = this.fcpTemplate;
			
			output.data = this.fcpTemplate.getBytes();
			return output;
		}
	}
}
