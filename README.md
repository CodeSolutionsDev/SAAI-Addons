

# SAAI-Addons

<div data-full-width="false">

<figure><img src="https://code-solutions.dev/assets/images/saai_heading.png" alt=""><figcaption></figcaption></figure>

</div>

<p align="center">
  <a href="https://wiki.code-solutions.dev/serverassistantai">
    <img src="https://code-solutions.dev/assets/images/wiki-button.png" alt="Wiki">
  </a>
  <a href="https://code-solutions.dev/discord">
    <img src="https://code-solutions.dev/assets/images/discord-button.png" alt="Discord">
  </a>
  <a href="https://code-solutions.dev">
    <img src="https://code-solutions.dev/assets/images/website-button.png" alt="Website">
  </a>
</p>
<p align="center">
  <a href="https://www.spigotmc.org/resources/serverassistantai.116241/">
    <img src="https://code-solutions.dev/assets/images/spigotmc-button.png" alt="Spigot">
  </a>
  <a href="https://builtbybit.com/resources/serverassistantai.43148/">
    <img src="https://code-solutions.dev/assets/images/bbb-button.png" alt="BBB">
  </a>
  <a href="https://polymart.org/resource/serverassistantai-33-off.5822">
    <img src="https://code-solutions.dev/assets/images/polymart-button.png" alt="Polymart">
  </a>
</p>

Welcome to the SAAI-Addons repository! This repository contains the source code for various addons that extend the functionality of the ServerAssistantAI Minecraft plugin. These addons are designed to improve the user experience and provide additional features to enhance the AI assistant's capabilities on your Minecraft server.

## Available Addons

Here are the currently available addons for ServerAssistantAI:

#### Anthropic Addon (LLM)

-   Description: Claude is a family of foundational AI models that can be used in a variety of applications. This addon allows ServerAssistantAI to utilize Anthropic's Claude models for LLM functionality.
-   Credentials: [`AnthropicCredentialsLoader.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/anthropic/src/main/java/dev/bluetree242/saaiaddons/anthropic/AnthropicCredentialsLoader.java) - Class responsible for loading Anthropic credentials.
-   Chat Model: [`AnthropicChatModelProvider.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/anthropic/src/main/java/dev/bluetree242/saaiaddons/anthropic/AnthropicChatModelProvider.java) - Implementation of the chat model provider for Anthropic.

#### Azure OpenAI Addon (LLM & Embedding)

-   Description: Azure OpenAI Service is a fully managed service that allows developers to easily integrate OpenAI models into their applications. This addon enables ServerAssistantAI to utilize Azure OpenAI for both LLM and embedding capabilities.
-   Credentials: [`AzureOpenAICredentials.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/azure-openai/src/main/java/dev/bluetree242/saaiaddons/azure/AzureOpenAICredentials.java) - Class responsible for loading Azure OpenAI credentials.
-   Chat Model: [`AzureOpenAIChatModelProvider.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/azure-openai/src/main/java/dev/bluetree242/saaiaddons/azure/AzureOpenAIChatModelProvider.java) - Implementation of the chat model provider for Azure OpenAI.
-   Embedding: [`AzureOpenAIEmbeddingProvider.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/azure-openai/src/main/java/dev/bluetree242/saaiaddons/azure/AzureOpenAIEmbeddingProvider.java) - Implementation of the embedding provider for Azure OpenAI.

#### HuggingFace Addon (LLM & Embedding)

-   Description: HuggingFace provides access to thousands of open-source models for free through the HuggingFace Inference API. This addon integrates HuggingFace models into ServerAssistantAI for both LLM and embedding functionality.
-   Credentials: [`HuggingFaceCredentialsLoader.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/hugging-face/src/main/java/dev/bluetree242/saaiaddons/huggingface/HuggingFaceCredentialsLoader.java) - Class responsible for loading HuggingFace credentials.
-   Chat Model: [`HuggingFaceChatModelProvider.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/hugging-face/src/main/java/dev/bluetree242/saaiaddons/huggingface/HuggingFaceChatModelProvider.java) - Implementation of the chat model provider for HuggingFace.
-   Embedding: [`HuggingFaceEmbeddingProvider.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/hugging-face/src/main/java/dev/bluetree242/saaiaddons/huggingface/HuggingFaceEmbeddingProvider.java) - Implementation of the embedding provider for HuggingFace.

#### Mistral AI Addon (LLM & Embedding)

-   Description: Mistral AI currently provides three types of access to Large Language Models. An API providing pay-as-you-go access to our latest models, Cloud-based deployments, Open source models available under the Apache 2.0 License, available on Hugging Face or directly from the documentation. This addon integrates Mistral AI models into ServerAssistantAI for both LLM and embedding.
-   Credentials: [`MistralAICredentialsLoader.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/mistral-ai/src/main/java/dev/bluetree242/saaiaddons/mistralai/MistralAICredentialsLoader.java) - Class responsible for loading Mistral AI credentials.
-   Chat Model: [`MistralAIChatModelProvider.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/mistral-ai/src/main/java/dev/bluetree242/saaiaddons/mistralai/MistralAIChatModelProvider.java) - Implementation of the chat model provider for Mistral AI.
-   Embedding: [`MistralAIEmbeddingProvider.java`](https://github.com/CodeSolutionsDev/SAAI-Addons/blob/main/mistral-ai/src/main/java/dev/bluetree242/saaiaddons/mistralai/MistralAIEmbeddingProvider.java) - Implementation of the embedding provider for Mistral AI.

More addons will be added over time, so stay tuned for updates!

## API

ServerAssistantAI provides a powerful [API](https://wiki.code-solutions.dev/serverassistantai/developers/api) for developers to integrate the AI assistant's functionality into their own plugins or applications, as well as create Addons for ServerAssistantAI.

The API is subject to change in future versions as we improve ServerAssistantAI and may result in breakages. You can join our [discord](https://code-solutions.dev/discord) server to make sure you're notified when a breaking change is done/will be done to ServerAssistantAI, so you can update your addons accordingly.

### JavaDoc

[![](https://img.shields.io/badge/JavaDoc-Released_API-4ac51c)](https://repo.bluetree242.dev/javadoc/releases/dev/bluetree242/serverassistantai/api/1.0/raw/index.html)

[![](https://img.shields.io/badge/JavaDoc-Development_API-FF7F7F)](https://repo.bluetree242.dev/javadoc/snapshots/dev/bluetree242/serverassistantai/api/1.1-SNAPSHOT/raw/index.html)

Please check the links to ensure you are working with the desired version. The badges below display the latest versions.

### API Version

[![](https://repo.bluetree242.dev/api/badge/latest/releases/dev/bluetree242/serverassistantai/api?name=Latest%20Release)](https://repo.bluetree242.dev/api/maven/versions/releases/dev/bluetree242/serverassistantai/api)

[![](https://repo.bluetree242.dev/api/badge/latest/snapshots/dev/bluetree242/serverassistantai/api?name=Latest%20Snapshot&color=FF7F7F)](https://repo.bluetree242.dev/api/maven/versions/snapshots/dev/bluetree242/serverassistantai/api)

ServerAssistantAI's API is public and accessible to all developers, even if they haven't purchased the plugin. As long as you obtain the plugin's JAR file legally, such as receiving it from someone for testing purposes, you can receive support for our API. However, using pirated or illegally obtained versions of ServerAssistantAI will not be eligible for support.

## License

This repository is licensed under the GNU General Public License v3.0. See the [LICENSE](https://github.com/CodeSolutionsDev/SAAI-Addons?tab=GPL-3.0-1-ov-file) file for more information.

## Contributing

We welcome contributions from the community! If you have an idea for a new addon or want to improve an existing one, feel free to open an issue or submit a pull request.

## Support

If you encounter any issues or have questions related to the addons or the API, please open an issue or join our [Discord](https://code-solutions.dev/discord) server and get quick support from our staff.
