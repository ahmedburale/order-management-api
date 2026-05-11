#!/bin/bash
set -e

echo "=== Pushing to GitHub ==="

echo "1. Initializing Git..."
git init

echo "2. Adding all files..."
git add .

echo "3. Committing..."
git commit -m "Initial commit: Order Management API"

echo "4. Adding remote..."
read -p "Enter your GitHub username: " USERNAME
git remote add origin "https://github.com/${ahmedburale}/order-management-api.git"

echo "5. Pushing..."
git branch -M main
git push -u origin main

echo "=== DONE! ==="
echo "View at: https://github.com/${ahmedburale}/order-management-api"
