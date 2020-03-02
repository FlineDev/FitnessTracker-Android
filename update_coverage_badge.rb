#!/usr/bin/env ruby

coverage_report = File.read('build/coverage-report/index.html')
match = coverage_report.match(
  /<td>Total<\/td><td class="bar">[\d\.,]+ of [\d\.,]+<\/td><td class="ctr2">([\d\.,]+)%<\/td>/
)
coverage = match.captures[0]

readme_contents = File.read('README.md')
readme_contents = readme_contents.gsub(/Coverage([-: ]+)[\d.,]+/, "Coverage\\1#{coverage}")
File.open('README.md', 'w') { |file| file.puts readme_contents }

puts "Updated coverage badge value to #{coverage}%."
