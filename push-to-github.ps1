Write-Host "Enter your GitHub repository URL (example: https://github.com/USERNAME/servercoreplus.git)"
$repoUrl = Read-Host "Repository URL"

if ([string]::IsNullOrWhiteSpace($repoUrl)) {
    Write-Host "No repository URL entered. Aborting."
    exit 1
}

git remote remove origin 2>$null
git remote add origin $repoUrl
git branch -M main
git push -u origin main
