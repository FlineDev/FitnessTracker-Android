#!/usr/bin/env ruby

coverage_report = File.read('build/coverage-report/index.html')
match = coverage_report.match(
  /<td>Total<\/td><td class="bar">[\d\.,]+ of [\d\.,]+<\/td><td class="ctr2">([\d\.,]+)%<\/td>/
)
coverage = match.captures[0]

readme_contents = File.read('README.md')
readme_contents = readme_contents.gsub(/Coverage: [\d.,]+/, "Coverage: #{coverage}")

color = case coverage.split('.')[0].to_i
when 0..30
  'red'
when 30..50
  'orange'
when 50..65
  'yellow'
when 65..75
  'yellowgreen'
when 75..85
  'green'
when 85..100
  'brightgreen'
else
  'blue'
end
readme_contents = readme_contents.gsub(/Coverage-[\d.,]+%25-\w+\.svg/, "Coverage-#{coverage}%25-#{color}.svg")

File.open('README.md', 'w') { |file| file.puts readme_contents }
puts "Updated coverage badge value to #{coverage}%."
