# File Encrypter and Decrypter

This Java program allows you to encrypt and decrypt files using the AES encryption algorithm. It supports both encryption and decryption modes with CBC cipher mode and PKCS5 padding.

## Getting Started

To use this program, follow the steps below:

1. Clone the repository or download the source code files.

2. Open the project in your preferred Java IDE.

3. Compile the `FitxerEnDeCrypter.java` file.

4. Run the program.

## Usage

When you run the program, it will prompt you for the file path and password. Follow the instructions and provide the necessary inputs.

If the file path ends with `.aes`, the program will decrypt the file and save it with the original name in the same directory.

If the file path does not end with `.aes`, the program will encrypt the file and save it with the `.aes` extension in the same directory.

After each encryption or decryption operation, the program will ask if you want to continue. Enter 'Y' or 'y' to continue with another file or 'N' or 'n' to exit the program.

## Requirements

- Java Development Kit (JDK)
- Java IDE or Compiler

## Notes

- This program uses AES encryption with a 192-bit key size.
- The initialization vector (IV) is hardcoded in the `ivParameter` variable. You can modify it if needed.
- The encrypted files are saved with the `.aes` extension.
- The original file is deleted after decryption.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvement, please open an issue or submit a pull request.



