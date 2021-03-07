# MobileDev_Project_ESILV_A4

Coded using Java.

### Usage:
When launching the app, you have to authenticate with your biometric (see Security)
You then have access to the app, you can refresh (refresh accounts button) to get the latest data from the API, or simply search for an account using your IBAN, if the account is found, it is displayed, if not, you have a "IBAN not found" Toast that is prompted.

### Security: The app requires a biometric to have been stored on the device: only devices with biometric authentication can use the app.
![image](https://user-images.githubusercontent.com/62998958/109495392-81b58100-7a8f-11eb-99fc-2ad22f9ead61.png)

In order to further enforce security, you cannot use the device pin/password to bypass the fingerprint authentication.
Here we used the wrong fingerprint :

![image](https://user-images.githubusercontent.com/62998958/109495518-ad386b80-7a8f-11eb-947c-3c1338890367.png)


And when trying to bypass authentication:


![image](https://user-images.githubusercontent.com/62998958/109495567-c04b3b80-7a8f-11eb-810b-5a4c9a7494e7.png)


(the app closes).

### Data Saving:
The personnal data is stored locally using a SQLite3 database. The database is refreshed everytime the user clicks on the "Refresh accounts" button.
Thus the accounts are available offline if they've been refreshed at least once, here is an example (notice airplane mode): 


![image](https://user-images.githubusercontent.com/62998958/109496205-a2320b00-7a90-11eb-95c5-e9ec882fdc38.png)

The database cannot be accessed by other apps, only a root user can access the file (and thus corrupt/steal the data)
I tried to implement SQLCipher to encrypt the database and further secure the data, but couldn't get it to work in due time.

### API Key:
The API key is hidden using C code in src/main/jni/keys.c, thanks to NDK. Using C means it can't be decompiled, but it could be accessed using a hexadecimal editor, but in order to further secure the APIKey, i encrypted it in base64, and entered a second "Fake" APIKey called "RealApiKey". 


### Permissions: 
The app only requests Internet and biometric permission, Internet in order to access the API and accounts, and Biometric in order to secure the access to the app.
