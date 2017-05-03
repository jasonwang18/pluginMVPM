package com.pluginmvpm.apt;

import javax.annotation.processing.RoundEnvironment;

public interface IProcessor {
    void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor);
}
