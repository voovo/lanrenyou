package com.lanrenyou.util.freemarker;


import freemarker.cache.FileTemplateLoader;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

public class FileTemplateLoader4EscapeHtml extends FileTemplateLoader {
    List<String> escapeExcludeDirs = Collections.emptyList();

    public FileTemplateLoader4EscapeHtml() throws IOException {
        super();
    }
    public FileTemplateLoader4EscapeHtml(File file) throws IOException {
        super(file);
    }
    public FileTemplateLoader4EscapeHtml(File file,List<String> escapeExcludeDirs) throws IOException {
        super(file);
        this.escapeExcludeDirs = escapeExcludeDirs;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        if(templateSource instanceof File){
            if(((File) templateSource).getName().startsWith("ne_") ||
                    escapeExcludeDirs.contains(((File) templateSource).getParent())){
                return super.getReader(templateSource,encoding);
            }
        }
        return new WrappingReader4EscapeHtml(super.getReader(templateSource, encoding), "<#escape x as x?html>", "</#escape>");
    }



}
