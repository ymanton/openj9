###############################################################################
# Copyright (c) 2016, 2018 IBM Corp. and others
#
# This program and the accompanying materials are made available under
# the terms of the Eclipse Public License 2.0 which accompanies this
# distribution and is available at https://www.eclipse.org/legal/epl-2.0/
# or the Apache License, Version 2.0 which accompanies this distribution and
# is available at https://www.apache.org/licenses/LICENSE-2.0.
#
# This Source Code may also be made available under the following
# Secondary Licenses when the conditions for such availability set
# forth in the Eclipse Public License, v. 2.0 are satisfied: GNU
# General Public License, version 2 with the GNU Classpath
# Exception [1] and GNU General Public License, version 2 with the
# OpenJDK Assembly Exception [2].
#
# [1] https://www.gnu.org/software/classpath/license.html
# [2] http://openjdk.java.net/legal/assembly-exception.html
#
# SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 OR LicenseRef-GPL-2.0 WITH Assembly-exception
###############################################################################

# Detect 64-bit vs. 32-bit.
TEMP_TARGET_DATASIZE := $(if $(findstring -64,$(SPEC)),64,32)

CONFIGURE_ARGS += \
<#if uma.spec.flags.opt_cuda.enabled>
  --enable-OMR_OPT_CUDA \
</#if>
  --enable-OMR_GC \
  --enable-OMR_PORT \
  --enable-OMR_THREAD \
  --enable-OMR_OMRSIG \
  --enable-tracegen \
<#if uma.spec.flags.env_64bit_capable.enabled>
  --enable-OMR_ENV_64BIT_CAPABLE \
</#if>
  --enable-OMR_GC_ARRAYLETS \
  --enable-OMR_GC_DYNAMIC_CLASS_UNLOADING \
  --enable-OMR_GC_MODRON_COMPACTION \
  --enable-OMR_GC_MODRON_CONCURRENT_MARK \
  --enable-OMR_GC_MODRON_SCAVENGER \
  --enable-OMR_GC_CONCURRENT_SWEEP \
  --enable-OMR_GC_SEGREGATED_HEAP \
  --enable-OMR_GC_HYBRID_ARRAYLETS \
  --enable-OMR_GC_LEAF_BITS \
  --enable-OMR_GC_REALTIME \
  --enable-OMR_GC_STACCATO \
  --enable-OMR_GC_VLHGC \
  --enable-OMR_PORT_ASYNC_HANDLER \
  --enable-OMR_THR_CUSTOM_SPIN_OPTIONS \
  --enable-OMR_NOTIFY_POLICY_CONTROL

# Configure OpenJ9 builds with DDR enabled to use tooling from OMR.
<#if uma.spec.flags.opt_useOmrDdr.enabled>
CONFIGURE_ARGS += --enable-debug --enable-DDR
<#else>
CONFIGURE_ARGS += --disable-debug
</#if>

CONFIGURE_ARGS += 'lib_output_dir=$$(top_srcdir)/../lib'
CONFIGURE_ARGS += 'exe_output_dir=$$(top_srcdir)/..'

# J9 needs include to compile Windows .rc files generated by UMA, because the .rc file references include/j9cfg.h
CONFIGURE_ARGS += 'GLOBAL_INCLUDES=$$(top_srcdir)/../include'

# This flag indicates that the J9 VMFarm build runs configure on a machine
# that is not capable of compiling the source code.
ifeq (yes,$(CALLED_BY_SOURCE_ZIP))
CONFIGURE_ARGS += 'OMR_CROSS_CONFIGURE=yes'
endif
