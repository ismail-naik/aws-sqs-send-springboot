# aws-sqs-send-springboot
Send Messages to AWS SQS using spring boot
#Adding or Configuring AWS credentials:

Create a IAM user in AWS and copy your credentials to connect to AWS environment using AWS JAVA JDK.
You can set Enviroment variable or Create a profile file as mentioned below.
1. Enviroment Variables
   Go your pc or laptop enviroment variable window and add two variable mentioned in under point 2.

2.Creating profile file:
  create a folder ".aws" under your userprofile path, under .aws create file with name credentials and copy and paste your credentials in the file and save it.
  For Example:
  [default]
  aws_access_key_id = <your access key>
  aws_secret_access_key = <your secret key>

  To your userprofile on windows type below command from command prompt
  echo %userprofile%
  The output will be: C:\Users\My Home
