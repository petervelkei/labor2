# Java Command-Line Tool

This repository contains a simple Java-based command-line tool that interprets various user commands (like `exit`, `hello`, `pwd`, etc.) and executes corresponding actions. Below is an overview of its features, how it works, and how to use it.

---

## Overview

- **Language**: Java
- **Main Class**: `Main`
- **Purpose**: Demonstrates an interactive console application where the user can type commands, and the application performs specific file-system and text-processing tasks.

---

## Features & Commands

The application reads commands from the user in a loop. For each recognized command, a specific method is called. The commands and their functions are:

1. **exit**  
   - **Usage**: `exit`  
   - **Description**: Prints a message indicating the program is shutting down, then terminates the application.

2. **hello**  
   - **Usage**: `hello`  
   - **Description**: Prints a friendly greeting message (`"Hello world!"`).

3. **pwd**  
   - **Usage**: `pwd`  
   - **Description**: Displays the current working directory (stored in the `wd` field).

4. **ls**  
   - **Usage**: `ls` or `ls -l`  
   - **Description**: Lists the contents of the current working directory.  
     - **`ls`**: Lists file and directory names.  
     - **`ls -l`**: Provides a detailed list including file type (`d` for directory, `f` for file) and file size in bytes.

5. **cd**  
   - **Usage**: `cd <dir>` or `cd ..`  
   - **Description**: Changes the current working directory to `<dir>`.  
     - **`cd ..`** moves to the parent directory.  
     - If the directory does not exist, an error message is shown.

6. **length**  
   - **Usage**: `length <file>`  
   - **Description**: Prints the size (in bytes) of the specified file. Displays an error if the file does not exist.

7. **grep**  
   - **Usage**: `grep <pattern> <file>`  
   - **Description**: Reads the contents of `<file>` and prints lines matching `<pattern>`. The matching uses a simple `String.matches(".*" + pattern + ".*")` check.

8. **tail**  
   - **Usage**: `tail [-n <number_of_lines>] <file>`  
   - **Description**: Prints the last *n* lines of `<file>`. By default, *n* is 10 if not specified.  
   - **Examples**:  
     - `tail test.txt` prints the last 10 lines of `test.txt`.  
     - `tail -n 5 test.txt` prints the last 5 lines of `test.txt`.

9. **sorszuro**  
   - **Usage**: `sorszuro <pattern>`  
   - **Description**: Continuously reads lines from **standard input** and prints only those that match `<pattern>`.

10. **filter**  
    - **Usage**: `filter [-i <inputFile>] [-o <outputFile>] [-p <pattern>]`  
    - **Description**: Reads lines either from `<inputFile>` (if specified) or standard input. Writes lines matching `<pattern>` to `<outputFile>` (if specified) or standard output.  
    - **Examples**:  
      - `filter -p error` reads from standard input and prints lines containing `error`.  
      - `filter -i input.txt -o output.txt -p hello` reads from `input.txt`, filters lines containing `hello`, and writes them to `output.txt`.

11. **gzip**  
    - **Usage**: `gzip -c <inputFile> <outputFile>` or `gzip -x <inputFile> <outputFile>`  
    - **Description**:  
      - `gzip -c`: Compresses `<inputFile>` into `<outputFile>` using GZIP.  
      - `gzip -x`: Decompresses (extracts) `<inputFile>` (which should be a GZIP file) into `<outputFile>`.  

12. **ujFuggveny**, **ujFuggveny2**, **ujFuggveny3**  
    - Additional placeholder methods for future development. Currently, they either have no body or simply print a message. They are not directly linked to any command in the main loop (unless you modify the code to do so).

---

## How It Works

1. **Main Loop**: The `main` method initializes a `Scanner` for reading user input.  
2. **Command Parsing**: Each line of input is split into tokens (using `String.split(" ")`).  
3. **Method Dispatch**: The first token is used to determine which method to call (e.g., `hello`, `ls`, `cd`, etc.).  
4. **Execution**: The method performs its logic (e.g., listing files, reading a file, filtering lines, etc.).  
5. **Repeat**: The process continues until the user enters `exit`.

---

## How to Compile & Run

1. **Ensure you have Java installed** (JDK 8 or higher).
2. **Compile** the program:
   ```bash
   javac Main.java
   ```
3. **Run** the compiled class:
   ```bash
   java Main
   ```
4. **Type commands** when prompted:
   ```text
   Adjon meg egy parancsot: hello
   Hello world!
   Adjon meg egy parancsot: pwd
   Aktuális könyvtár: ...
   Adjon meg egy parancsot: exit
   A program leáll.
   ```

---

## Notes

- **File Paths**: All file operations are relative to the current working directory (`wd`), which is initially set to the directory where the program is launched.  
- **Regex Matching**: Some commands (like `grep` or `sorszuro`) use a simple `line.matches(".*" + pattern + ".*")` approach, which means the `<pattern>` is interpreted as part of a regular expression. Special regex characters could affect matching.  
- **Error Handling**: The code prints error messages for invalid inputs, missing files, or other exceptions (e.g., `IOException`).  
- **Extensions**: You can add more commands or modify existing ones by expanding the `if-else` chain in the `main` method or hooking up the placeholder functions (`ujFuggveny`, `ujFuggveny2`, `ujFuggveny3`).

---

## Contributing

Feel free to open issues, submit pull requests, or modify this code for your own projects. Additional commands or improvements (e.g., more robust error handling, improved regex usage, etc.) are always welcome.

---

**Happy coding and exploring!** If you have any questions or encounter any issues, please let me know.
