/*******************************************************************************
 * Copyright (c) 1991, 2019 IBM Corp. and others
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which accompanies this
 * distribution and is available at https://www.eclipse.org/legal/epl-2.0/
 * or the Apache License, Version 2.0 which accompanies this distribution and
 * is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following
 * Secondary Licenses when the conditions for such availability set
 * forth in the Eclipse Public License, v. 2.0 are satisfied: GNU
 * General Public License, version 2 with the GNU Classpath
 * Exception [1] and GNU General Public License, version 2 with the
 * OpenJDK Assembly Exception [2].
 *
 * [1] https://www.gnu.org/software/classpath/license.html
 * [2] http://openjdk.java.net/legal/assembly-exception.html
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
 *******************************************************************************/
package com.ibm.j9ddr.vm29.pointer.helper;

import com.ibm.j9ddr.CorruptDataException;
import com.ibm.j9ddr.vm29.pointer.AbstractPointer;
import com.ibm.j9ddr.vm29.pointer.U8Pointer;
import com.ibm.j9ddr.vm29.pointer.generated.J9ShrOffSetPointer;
import com.ibm.j9ddr.vm29.pointer.generated.ByteDataWrapperPointer;
import com.ibm.j9ddr.vm29.structure.ByteDataWrapper;
import com.ibm.j9ddr.vm29.types.I32;
import com.ibm.j9ddr.vm29.types.U16;
import com.ibm.j9ddr.vm29.types.U32;
import com.ibm.j9ddr.vm29.types.U8;

public class ByteDataWrapperHelper {
//	#define BDWITEM(bdw) (((U_8*)(bdw)) - sizeof(ShcItem))

	
//	#define BDWEXTBLOCK(bdw) J9SHR_READMEM((bdw)->externalBlockOffset)
	public static I32 BDWEXTBLOCK(ByteDataWrapperPointer ptr, U8Pointer[] cacheHeader) throws CorruptDataException {
		if (null == cacheHeader) {
			return new I32(ptr.externalBlockOffset());
		} else {
			if (J9ShrOffSetPointer.cast(ptr.externalBlockOffsetEA()).offset().eq(0)) {
				return new I32(0);
			}
			return new I32(J9ShrOffSetPointer.cast(ptr.externalBlockOffsetEA()).offset());
		}
	}

//	#define BDWLEN(bdw) J9SHR_READMEM((bdw)->dataLength)	
	public static U32 BDWLEN(ByteDataWrapperPointer ptr) throws CorruptDataException {
		return new U32(ptr.dataLength());
	}
	
//	#define BDWTYPE(bdw)  J9SHR_READMEM((bdw)->dataType)
	public static U8 BDWTYPE(ByteDataWrapperPointer ptr) throws CorruptDataException {
		return ptr.dataType();
	}

	public static U8Pointer getDataFromByteDataWrapper(ByteDataWrapperPointer ptr, U8Pointer[] cacheHeader) throws CorruptDataException {
		if (null == cacheHeader) {
			if(!ptr.externalBlockOffset().eq(0)) {
				return U8Pointer.cast(ptr).add(ptr.externalBlockOffset()); 
			} else {
				return U8Pointer.cast(ptr).add(ByteDataWrapper.SIZEOF);
			}
		} else {
			if (J9ShrOffSetPointer.cast(ptr.externalBlockOffsetEA()).offset().eq(0)) {
				/* return the same as #define BDWDATA(bdw) (((U_8*)(bdw)) + sizeof(ByteDataWrapper)) */
				return U8Pointer.cast(ptr).add(ByteDataWrapper.SIZEOF);
			} else {
				/* return the same as SH_CacheMap::getDataFromByteDataWrapper(const ByteDataWrapper* bdw) */
				return cacheHeader[0].add(J9ShrOffSetPointer.cast(ptr.externalBlockOffsetEA()).offset());
			}
		}
	}
//	#define BDWINPRIVATEUSE(bdw) J9SHR_READMEM((bdw)->inPrivateUse)
	public static U8 BDWINPRIVATEUSE(ByteDataWrapperPointer ptr) throws CorruptDataException {		
		return ptr.inPrivateUse();
	}
//	#define BDWPRIVATEOWNERID(bdw) J9SHR_READMEM((bdw)->privateOwnerID)
	public static U16 BDWPRIVATEOWNERID(ByteDataWrapperPointer ptr) throws CorruptDataException { 
		return ptr.privateOwnerID();
	}

	public static AbstractPointer BDWTOKEN(ByteDataWrapperPointer ptr, U8Pointer[] cacheHeader) throws CorruptDataException {
		if (null == cacheHeader) {
			if(!ptr.tokenOffset().eq(0)) {
				return U8Pointer.cast(ptr).add(ptr.tokenOffset());
			} else {
				return U8Pointer.NULL;
			}
		} else {
			if (J9ShrOffSetPointer.cast(ptr.tokenOffsetEA()).offset().eq(0)) {
				return U8Pointer.NULL;
			}
			return cacheHeader[0].add(J9ShrOffSetPointer.cast(ptr.tokenOffsetEA()).offset());
		}
	}
}
