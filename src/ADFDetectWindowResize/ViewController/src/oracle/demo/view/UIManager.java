/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view;
/*-------------------------------------------------------------------------------------------
 * This code is distributed under the MIT License (MIT)
 *
 * Copyright (c) 2012 Duncan Mills
 *-------------------------------------------------------------------------------------------
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 *-------------------------------------------------------------------------------------------*/


/**
 * Session scoped data manager
 */
public class UIManager {
    
    private int _windowWidth;
    private int _windowHeight;
    
    public void setWindowWidth(int _windowWidth) {
        this._windowWidth = _windowWidth;
    }

    public int getWindowWidth() {
        return _windowWidth;
    }

    public void setWindowHeight(int _windowHeight) {
        this._windowHeight = _windowHeight;
    }

    public int getWindowHeight() {
        return _windowHeight;
    }
}
