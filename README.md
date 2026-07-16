<div align="center">

# HTTP World Clock Server

### A custom HTTP server built in Java that serves dynamic web pages displaying live world times using raw socket programming and the HTTP protocol.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![HTTP](https://img.shields.io/badge/HTTP-Protocol-blue?style=for-the-badge)
![TCP](https://img.shields.io/badge/TCP-Sockets-success?style=for-the-badge)
![Networking](https://img.shields.io/badge/Computer-Networks-orange?style=for-the-badge)

**Author:** **Lwandle Chauke**

</div>

---

# Overview

The **HTTP World Clock Server** is a Java networking application that implements a lightweight HTTP server from scratch using raw TCP sockets.

Instead of relying on web frameworks or existing HTTP libraries, the server listens for browser requests, interprets HTTP GET requests, dynamically generates HTML pages, and returns valid HTTP responses directly to the client.

The application displays the current local time in South Africa alongside the current time in several major cities around the world. Users can switch between cities through hyperlinks, while the displayed time automatically refreshes without requiring manual page reloads.

This project provided hands-on experience with the HTTP protocol, socket programming, browser-server communication, and dynamic HTML generation.

---

# Project Highlights

- Custom HTTP server implementation
- Java TCP socket programming
- Dynamic HTML generation
- Browser-based interaction
- HTTP request parsing
- HTTP response generation
- Automatic page refresh
- Time zone calculations
- Server-side rendering
- No external web frameworks

---

# Features

## HTTP Server

- Listens for incoming HTTP connections
- Handles browser requests
- Parses HTTP GET requests
- Generates valid HTTP responses
- Returns dynamic HTML content

---

## World Clock

- Displays South African local time
- Displays selected world city time
- Automatic clock updates
- Multiple supported cities
- Real-time time zone conversion

---

## Browser Interface

- Interactive hyperlinks
- Automatic page refresh
- Simple web interface
- Dynamic server-generated pages
- Compatible with standard web browsers

---

# Networking Concepts

This project demonstrates several fundamental networking concepts.

## HTTP Protocol

- HTTP GET requests
- HTTP response messages
- HTTP headers
- Content-Type handling
- Browser-server communication

## TCP Communication

- TCP sockets
- Server socket implementation
- Client connections
- Stream-based communication
- Request-response model

## Client-Server Architecture

- Browser as client
- Java application as server
- Stateless communication
- Dynamic content generation

---

# Technologies Used

## Core Technologies

- Java
- TCP Sockets
- HTTP
- HTML
- Java Networking API

## Java Packages

- java.net
- java.io
- java.time
- java.util

---

# System Architecture

```text
                  Web Browser
                        │
                        │ HTTP GET Request
                        ▼
          Java HTTP Server (TCP Socket)
                        │
                        ▼
             Request Processing Engine
                        │
                        ▼
             Time Zone Calculation
                        │
                        ▼
         Dynamic HTML Response Builder
                        │
                        ▼
             HTTP Response (HTML)
                        │
                        ▼
                  Web Browser
```

---

# Repository Structure

```text
http-world-clock-server/

│
├── src/
│   ├── HTTPServer.java
│   ├── RequestHandler.java
│   ├── WorldClock.java
│   ├── HTMLGenerator.java
│   └── Utilities.java
│
├── docs/
│   ├── Assignment Specification.pdf
│   ├── Screenshots/
│   └── Design Notes.pdf
│
├── screenshots/
│
├── README.md
│
└── LICENSE
```

---

# How It Works

When the application starts, it opens a TCP server socket on a specified port.

A web browser connects to the server by navigating to:

```
http://localhost:55555
```

The server:

1. Waits for an incoming connection.
2. Reads the HTTP request.
3. Extracts the requested path.
4. Determines which city was selected.
5. Retrieves the current time.
6. Generates a new HTML page.
7. Sends an HTTP response back to the browser.

The page automatically refreshes at regular intervals using HTML meta refresh tags, ensuring that the displayed times remain current.

---

# Example Workflow

### Step 1

Start the Java server.

```bash
java HTTPServer
```

---

### Step 2

Open a browser.

```
http://localhost:55555
```

---

### Step 3

The browser sends:

```http
GET / HTTP/1.1
Host: localhost
```

---

### Step 4

The server generates:

- South African time
- Selected city's time
- Navigation links

---

### Step 5

The browser renders the generated HTML page.

---

# Skills Demonstrated

This project demonstrates experience with:

- Socket programming
- HTTP protocol implementation
- Java networking
- Server development
- Dynamic HTML generation
- Client-server communication
- Time zone manipulation
- Request parsing
- Response generation
- Software architecture

---

# Running the Project

## Clone the Repository

```bash
git clone https://github.com/Lwandle-Chauke/http-world-clock-server.git
```

Compile the application.

```bash
javac *.java
```

Run the server.

```bash
java HTTPServer
```

Open your browser.

```
http://localhost:55555
```

---

# Documentation

The repository includes documentation covering:

- Assignment requirements
- Server architecture
- HTTP message format
- Socket implementation
- Design decisions
- Screenshots
- Testing results

---

# Screenshots

The **screenshots** directory contains examples of:

- Home page
- World clock interface
- City selection
- Dynamic updates
- Browser interaction

---

# Learning Outcomes

This project significantly improved my understanding of:

- HTTP protocol internals
- TCP socket programming
- Web server architecture
- Browser-server communication
- Dynamic web page generation
- Request parsing
- Response formatting
- Java networking APIs

Implementing an HTTP server from scratch provided valuable insight into how modern web servers process requests and generate responses.

---

# Future Improvements

Potential enhancements include:

- HTTPS support
- Multi-threaded client handling
- REST API endpoints
- CSS styling improvements
- JavaScript enhancements
- Additional world cities
- Daylight Saving Time detection
- Time zone database integration
- Docker deployment
- Cloud hosting

---

# About Me

I'm **Lwandle Chauke**, a Computer Science graduate with a strong interest in:

- Software Engineering
- Computer Networks
- Cybersecurity
- Backend Development
- Distributed Systems
- Application Security

I'm passionate about building software that combines networking, programming, and secure system design while continuously expanding my technical expertise.

**GitHub**

https://github.com/Lwandle-Chauke

---

<div align="center">

If you found this project interesting, feel free to star the repository.

</div>
