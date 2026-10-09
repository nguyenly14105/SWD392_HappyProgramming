package com.happyprogramming.modules.ai;

import com.happyprogramming.modules.ai.dto.AiChatRequest;
import com.happyprogramming.modules.ai.dto.AiChatResponse;

public interface AiService {
    AiChatResponse chat(AiChatRequest request);
}
