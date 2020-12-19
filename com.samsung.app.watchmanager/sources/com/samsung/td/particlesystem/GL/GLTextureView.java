package com.samsung.td.particlesystem.GL;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

public class GLTextureView extends TextureView implements TextureView.SurfaceTextureListener, View.OnLayoutChangeListener {

    /* renamed from: a  reason: collision with root package name */
    GLSurfaceView.Renderer f1905a;

    /* renamed from: b  reason: collision with root package name */
    a f1906b = null;

    private class a extends Thread {

        /* renamed from: a  reason: collision with root package name */
        private volatile boolean f1907a;

        /* renamed from: b  reason: collision with root package name */
        private final SurfaceTexture f1908b;

        /* renamed from: c  reason: collision with root package name */
        private EGL10 f1909c;

        /* renamed from: d  reason: collision with root package name */
        private EGLDisplay f1910d;
        private EGLConfig e;
        private EGLContext f;
        private EGLSurface g;
        private GL h;
        private int i = GLTextureView.this.getWidth();
        private int j = GLTextureView.this.getHeight();
        private volatile boolean k = true;

        a(SurfaceTexture surfaceTexture) {
            this.f1908b = surfaceTexture;
        }

        private void c() {
            if (!this.f.equals(this.f1909c.eglGetCurrentContext()) || !this.g.equals(this.f1909c.eglGetCurrentSurface(12377))) {
                d();
                EGL10 egl10 = this.f1909c;
                EGLDisplay eGLDisplay = this.f1910d;
                EGLSurface eGLSurface = this.g;
                if (egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f)) {
                    d();
                    return;
                }
                throw new RuntimeException("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.f1909c.eglGetError()));
            }
        }

        private void d() {
            int eglGetError = this.f1909c.eglGetError();
            if (eglGetError != 12288) {
                Log.e("PanTextureView", "EGL error = 0x" + Integer.toHexString(eglGetError));
            }
        }

        private EGLConfig e() {
            int[] iArr = new int[1];
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            if (!this.f1909c.eglChooseConfig(this.f1910d, h(), eGLConfigArr, 1, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig failed " + GLUtils.getEGLErrorString(this.f1909c.eglGetError()));
            } else if (iArr[0] > 0) {
                return eGLConfigArr[0];
            } else {
                return null;
            }
        }

        private void f() {
            EGLSurface eGLSurface;
            EGLSurface eGLSurface2 = this.g;
            if (eGLSurface2 != null && eGLSurface2 != (eGLSurface = EGL10.EGL_NO_SURFACE)) {
                this.f1909c.eglMakeCurrent(this.f1910d, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
                this.f1909c.eglDestroySurface(this.f1910d, this.g);
                this.g = null;
            }
        }

        private void g() {
            this.f1909c.eglDestroyContext(this.f1910d, this.f);
            this.f1909c.eglTerminate(this.f1910d);
            this.f1909c.eglDestroySurface(this.f1910d, this.g);
        }

        private int[] h() {
            return new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344};
        }

        private void i() {
            this.f1909c = (EGL10) EGLContext.getEGL();
            this.f1910d = this.f1909c.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            EGLDisplay eGLDisplay = this.f1910d;
            if (eGLDisplay != EGL10.EGL_NO_DISPLAY) {
                if (this.f1909c.eglInitialize(eGLDisplay, new int[2])) {
                    this.e = e();
                    EGLConfig eGLConfig = this.e;
                    if (eGLConfig != null) {
                        this.f = a(this.f1909c, this.f1910d, eGLConfig);
                        a();
                        EGL10 egl10 = this.f1909c;
                        EGLDisplay eGLDisplay2 = this.f1910d;
                        EGLSurface eGLSurface = this.g;
                        if (egl10.eglMakeCurrent(eGLDisplay2, eGLSurface, eGLSurface, this.f)) {
                            this.h = this.f.getGL();
                            return;
                        }
                        throw new RuntimeException("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.f1909c.eglGetError()));
                    }
                    throw new RuntimeException("eglConfig not initialized");
                }
                throw new RuntimeException("eglInitialize failed " + GLUtils.getEGLErrorString(this.f1909c.eglGetError()));
            }
            throw new RuntimeException("eglGetDisplay failed " + GLUtils.getEGLErrorString(this.f1909c.eglGetError()));
        }

        /* access modifiers changed from: package-private */
        public EGLContext a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
        }

        public synchronized void a(int i2, int i3) {
            this.i = i2;
            this.j = i3;
            this.k = true;
        }

        public boolean a() {
            if (this.f1909c == null) {
                throw new RuntimeException("egl not initialized");
            } else if (this.f1910d == null) {
                throw new RuntimeException("eglDisplay not initialized");
            } else if (this.e != null) {
                f();
                try {
                    this.g = this.f1909c.eglCreateWindowSurface(this.f1910d, this.e, this.f1908b, null);
                    EGLSurface eGLSurface = this.g;
                    if (eGLSurface == null || eGLSurface == EGL10.EGL_NO_SURFACE) {
                        if (this.f1909c.eglGetError() == 12299) {
                            Log.e("GLTextureView", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                        }
                        return false;
                    } else if (this.f1909c.eglMakeCurrent(this.f1910d, eGLSurface, eGLSurface, this.f)) {
                        return true;
                    } else {
                        Log.e("GLTextureView", "eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.f1909c.eglGetError()));
                        return false;
                    }
                } catch (IllegalArgumentException e2) {
                    Log.e("GLTextureView", "eglCreateWindowSurface", e2);
                    return false;
                }
            } else {
                throw new RuntimeException("eglConfig not initialized");
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            this.f1907a = true;
        }

        public void run() {
            i();
            GL10 gl10 = (GL10) this.h;
            GLTextureView.this.f1905a.onSurfaceCreated(gl10, this.e);
            while (!this.f1907a) {
                c();
                if (this.k) {
                    a();
                    GLTextureView.this.f1905a.onSurfaceChanged(gl10, this.i, this.j);
                    this.k = false;
                }
                GLTextureView.this.f1905a.onDrawFrame(gl10);
                if (!this.f1909c.eglSwapBuffers(this.f1910d, this.g)) {
                    throw new RuntimeException("Cannot swap buffers");
                }
            }
            g();
        }
    }

    public GLTextureView(Context context) {
        super(context);
        setOpaque(false);
        setSurfaceTextureListener(this);
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.f1906b = new a(surfaceTexture);
        this.f1906b.start();
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.f1906b.b();
        return false;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        this.f1906b.a(i, i2);
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void setRenderer(GLSurfaceView.Renderer renderer) {
        this.f1905a = renderer;
    }
}
