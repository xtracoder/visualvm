/*
 * Copyright (c) 2010, 2018, Oracle and/or its affiliates. All rights reserved.
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

package org.graalvm.visualvm.lib.profiler.oql.repository.api;

import java.util.List;
import org.netbeans.api.annotations.common.NonNull;

/**
 * Value object for an OQL query category<br/>
 * Use {@linkplain OQLQueryRepository#listCategories()} or its variants
 * to obtain this class instances.
 * @author Jaroslav Bachorik
 */
final public class OQLQueryCategory {
    private final String id;
    private final String name;
    private final String description;
    private final OQLQueryRepository repository;

    OQLQueryCategory(@NonNull OQLQueryRepository repository, @NonNull String id,
                     @NonNull String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.repository = repository;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    String getID() {
        return id;
    }

    @NonNull
    public List<? extends OQLQueryDefinition> listQueries() {
        return repository.listQueries(this);
    }

    @NonNull
    public List<? extends OQLQueryDefinition> listQueries(@NonNull String pattern) {
        return repository.listQueries(this, pattern);
    }

    @Override
    public String toString() {
        return name;
    }
}
