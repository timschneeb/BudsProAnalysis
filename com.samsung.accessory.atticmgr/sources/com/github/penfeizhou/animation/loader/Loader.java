package com.github.penfeizhou.animation.loader;

import com.github.penfeizhou.animation.io.Reader;
import java.io.IOException;

public interface Loader {
    Reader obtain() throws IOException;
}
