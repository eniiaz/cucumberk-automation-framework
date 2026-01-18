@nomadtms @ui @agent
Feature: Nomad TMS AI Agent Chat
  As a fleet manager
  I want to interact with the TMS AI Agent
  So that I can get intelligent assistance for fleet management tasks

  Background:
    Given the user is logged in to Nomad TMS
    And the user navigates to the Agent chat page

  @smoke @agent-conversation
  Scenario Outline: Agent responds appropriately to different message types
    When the user sends message "<message>"
    Then the Agent should respond within TMS domain scope
    And the response should be validated as "<expected_behavior>"

    Examples:
      | message                                                              | expected_behavior                        |
      | Hello, hi!                                                           | friendly greeting offering assistance    |
      | What can you do for me?                                              | explains TMS capabilities                |
      | What is the capital of France? Can you help me with cooking recipes? | politely declines non-TMS requests       |
