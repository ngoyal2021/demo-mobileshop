# Gemfile
# Ruby dependency configuration for Windows 10
# This file defines only the essential gems needed for Sauce Labs automation

source "https://rubygems.org"

# Fastlane is the core automation framework that orchestrates everything
# We're using a well-tested version that's confirmed to work reliably on Windows
# This gem provides the lane system, the UI helpers, and the plugin architecture
gem "fastlane", "~> 2.219"

# RestClient is an HTTP client library that makes API calls simple
# We use this extensively to interact with Sauce Labs' REST API
# It handles authentication, multipart file uploads, and error responses gracefully
gem "rest-client", "~> 2.1"

# HTTParty is an alternative HTTP client with a more elegant API design
# We include this as a backup option for complex API interactions
# Some developers prefer its syntax for chaining requests and handling responses
gem "httparty", "~> 0.21"

# JSON parsing is essential because all Sauce Labs API responses come back as JSON
# This gem lets us convert JSON strings into Ruby hashes and vice versa
# Ruby includes a basic JSON library, but this gem is faster and more robust
gem "json", "~> 2.6"

# Dotenv loads environment variables from a .env file into ENV
# This keeps sensitive credentials like your Sauce Labs API key out of your code
# It's a security best practice - your .env file stays local and never gets committed to Git
gem "dotenv", "~> 2.8"

# These next two gems are Windows-specific and only install if you're on Windows
# The conditional check uses Ruby's platform detection

# Pathname provides better path manipulation on Windows
# It helps us normalize paths between Windows backslash style and Unix forward slash style
gem "pathname", "~> 0.2" if Gem.win_platform?

# Win32-process gives us better control over Windows process management
# This helps when we need to spawn Gradle or ADB commands and monitor their execution
gem "win32-process", "~> 0.9" if Gem.win_platform?

# The Fastlane plugin system looks for a Pluginfile
# We include this check so that if you add plugins later, they'll be loaded automatically
# For now, this file won't exist, and that's perfectly fine
plugins_path = File.join(File.dirname(__FILE__), 'fastlane', 'Pluginfile')
eval_gemfile(plugins_path) if File.exist?(plugins_path)