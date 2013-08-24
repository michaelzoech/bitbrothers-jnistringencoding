#include <jni.h>
#include <string.h>

#ifdef __cplusplus
extern "C"
{
#endif


JNIEXPORT jbyteArray JNICALL Java_org_bitbrothers_example_jnistringencoding_NativeLayer_convertStringToByteArray(JNIEnv* env, jclass clazz, jstring str)
{
	const char* nativeString = env->GetStringUTFChars(str, 0);
	size_t length = strlen(nativeString);

	jbyteArray array = env->NewByteArray(length);
	env->SetByteArrayRegion(array, 0, length, (const jbyte*)nativeString);

	env->ReleaseStringUTFChars(str, nativeString);

	return array;
}

JNIEXPORT jstring JNICALL Java_org_bitbrothers_example_jnistringencoding_NativeLayer_convertByteArrayToString(JNIEnv* env, jclass clazz, jbyteArray array)
{
	jsize length = env->GetArrayLength(array);
	jbyte* buffer = new jbyte[length+1];

	env->GetByteArrayRegion(array, 0, length, buffer);
	buffer[length] = '\0';

	jstring result = env->NewStringUTF((const char*)buffer);

	delete[] buffer;

	return result;
}

#ifdef __cplusplus
}
#endif
