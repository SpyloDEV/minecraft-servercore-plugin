@echo off
echo Initializing local git repository...
if not exist .git (
  git init
)
git add .
git commit -m "Initial commit - ServerCorePlus"
echo.
echo Done. Next step:
echo powershell -ExecutionPolicy Bypass -File .\push-to-github.ps1
pause
