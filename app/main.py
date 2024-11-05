# app/main.py
from fastapi import FastAPI, Request
import subprocess  # For running shell commands

app = FastAPI()

@app.get("/")
async def root():
    return {"message": "Hello, FastAPI in Docker!"}

