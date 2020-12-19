package androidx.core.view.inputmethod;

import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

public final class InputConnectionCompat {
    private static final String COMMIT_CONTENT_ACTION = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    private static final String COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    private static final String COMMIT_CONTENT_CONTENT_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    private static final String COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    private static final String COMMIT_CONTENT_DESCRIPTION_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    private static final String COMMIT_CONTENT_FLAGS_INTEROP_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    private static final String COMMIT_CONTENT_FLAGS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    private static final String COMMIT_CONTENT_INTEROP_ACTION = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    private static final String COMMIT_CONTENT_LINK_URI_INTEROP_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    private static final String COMMIT_CONTENT_LINK_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    private static final String COMMIT_CONTENT_OPTS_INTEROP_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    private static final String COMMIT_CONTENT_OPTS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    private static final String COMMIT_CONTENT_RESULT_INTEROP_RECEIVER_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    private static final String COMMIT_CONTENT_RESULT_RECEIVER_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    public interface OnCommitContentListener {
        boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x005d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean handlePerformPrivateCommand(java.lang.String r9, android.os.Bundle r10, androidx.core.view.inputmethod.InputConnectionCompat.OnCommitContentListener r11) {
        /*
            java.lang.String r0 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS"
            java.lang.String r1 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS"
            java.lang.String r2 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI"
            java.lang.String r3 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION"
            java.lang.String r4 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI"
            java.lang.String r5 = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER"
            r6 = 0
            if (r10 != 0) goto L_0x0010
            return r6
        L_0x0010:
            java.lang.String r7 = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT"
            boolean r8 = android.text.TextUtils.equals(r7, r9)
            if (r8 == 0) goto L_0x001a
            r9 = r6
            goto L_0x0021
        L_0x001a:
            boolean r9 = android.text.TextUtils.equals(r7, r9)
            if (r9 == 0) goto L_0x0061
            r9 = 1
        L_0x0021:
            r7 = 0
            android.os.Parcelable r5 = r10.getParcelable(r5)     // Catch:{ all -> 0x0059 }
            android.os.ResultReceiver r5 = (android.os.ResultReceiver) r5     // Catch:{ all -> 0x0059 }
            android.os.Parcelable r4 = r10.getParcelable(r4)     // Catch:{ all -> 0x0057 }
            android.net.Uri r4 = (android.net.Uri) r4     // Catch:{ all -> 0x0057 }
            android.os.Parcelable r3 = r10.getParcelable(r3)     // Catch:{ all -> 0x0057 }
            android.content.ClipDescription r3 = (android.content.ClipDescription) r3     // Catch:{ all -> 0x0057 }
            android.os.Parcelable r2 = r10.getParcelable(r2)     // Catch:{ all -> 0x0057 }
            android.net.Uri r2 = (android.net.Uri) r2     // Catch:{ all -> 0x0057 }
            int r1 = r10.getInt(r1)     // Catch:{ all -> 0x0057 }
            android.os.Parcelable r9 = r10.getParcelable(r0)     // Catch:{ all -> 0x0057 }
            android.os.Bundle r9 = (android.os.Bundle) r9     // Catch:{ all -> 0x0057 }
            if (r4 == 0) goto L_0x0051
            if (r3 == 0) goto L_0x0051
            androidx.core.view.inputmethod.InputContentInfoCompat r10 = new androidx.core.view.inputmethod.InputContentInfoCompat     // Catch:{ all -> 0x0057 }
            r10.<init>(r4, r3, r2)     // Catch:{ all -> 0x0057 }
            boolean r6 = r11.onCommitContent(r10, r1, r9)     // Catch:{ all -> 0x0057 }
        L_0x0051:
            if (r5 == 0) goto L_0x0056
            r5.send(r6, r7)
        L_0x0056:
            return r6
        L_0x0057:
            r9 = move-exception
            goto L_0x005b
        L_0x0059:
            r9 = move-exception
            r5 = r7
        L_0x005b:
            if (r5 == 0) goto L_0x0060
            r5.send(r6, r7)
        L_0x0060:
            throw r9
        L_0x0061:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.view.inputmethod.InputConnectionCompat.handlePerformPrivateCommand(java.lang.String, android.os.Bundle, androidx.core.view.inputmethod.InputConnectionCompat$OnCommitContentListener):boolean");
    }

    public static boolean commitContent(InputConnection inputConnection, EditorInfo editorInfo, InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle) {
        boolean z;
        ClipDescription description = inputContentInfoCompat.getDescription();
        String[] contentMimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo);
        int length = contentMimeTypes.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z = false;
                break;
            } else if (description.hasMimeType(contentMimeTypes[i2])) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (!z) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 25) {
            return inputConnection.commitContent((InputContentInfo) inputContentInfoCompat.unwrap(), i, bundle);
        }
        int protocol = EditorInfoCompat.getProtocol(editorInfo);
        if (protocol != 2) {
            if (!(protocol == 3 || protocol == 4)) {
                return false;
            }
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI", inputContentInfoCompat.getContentUri());
        bundle2.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION", inputContentInfoCompat.getDescription());
        bundle2.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI", inputContentInfoCompat.getLinkUri());
        bundle2.putInt("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS", i);
        bundle2.putParcelable("androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS", bundle);
        return inputConnection.performPrivateCommand("androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", bundle2);
    }

    public static InputConnection createWrapper(InputConnection inputConnection, EditorInfo editorInfo, final OnCommitContentListener onCommitContentListener) {
        if (inputConnection == null) {
            throw new IllegalArgumentException("inputConnection must be non-null");
        } else if (editorInfo == null) {
            throw new IllegalArgumentException("editorInfo must be non-null");
        } else if (onCommitContentListener == null) {
            throw new IllegalArgumentException("onCommitContentListener must be non-null");
        } else if (Build.VERSION.SDK_INT >= 25) {
            return new InputConnectionWrapper(inputConnection, false) {
                /* class androidx.core.view.inputmethod.InputConnectionCompat.AnonymousClass1 */

                public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
                    if (onCommitContentListener.onCommitContent(InputContentInfoCompat.wrap(inputContentInfo), i, bundle)) {
                        return true;
                    }
                    return super.commitContent(inputContentInfo, i, bundle);
                }
            };
        } else {
            if (EditorInfoCompat.getContentMimeTypes(editorInfo).length == 0) {
                return inputConnection;
            }
            return new InputConnectionWrapper(inputConnection, false) {
                /* class androidx.core.view.inputmethod.InputConnectionCompat.AnonymousClass2 */

                public boolean performPrivateCommand(String str, Bundle bundle) {
                    if (InputConnectionCompat.handlePerformPrivateCommand(str, bundle, onCommitContentListener)) {
                        return true;
                    }
                    return super.performPrivateCommand(str, bundle);
                }
            };
        }
    }
}
