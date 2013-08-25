## Dalvik/JNI String Encoding example

A sample application that shows the difference between UTF-8 and Modified UTF-8.
Modified UTF-8 is used by the JVM and Dalvik internally, but surfaces in JNI where all string methods expect strings to be in Modified UTF-8.

Please note that the sample may crash when pressing the "Convert" button and a string is given that results in different UTF-8 encodings.
This is for demonstration purposes.
