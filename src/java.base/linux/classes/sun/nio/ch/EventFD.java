/*
 * Copyright (c) 2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package sun.nio.ch;

import java.io.IOException;

/*
 * Provides access to the Linux eventfd object.
 */
final class EventFD {
    private final int efd;

    /**
     * Creates a blocking eventfd object with initial value zero.
     */
    EventFD() throws IOException {
        efd = eventfd0();
    }

    int efd() {
        return efd;
    }

    void set() throws IOException {
        set0(efd);
    }

    void reset() throws IOException {
        IOUtil.drain(efd);
    }

    void close() throws IOException {
        FileDispatcherImpl.closeIntFD(efd);
    }

    private static native int eventfd0() throws IOException;

    /**
     * Writes the value 1 to the eventfd object as a long in the
     * native byte order of the platform.
     *
     * @param the integral eventfd file descriptor
     * @return the number of bytes written; should equal 8
     */
    private static native int set0(int efd) throws IOException;

    static {
        IOUtil.load();
    }
}