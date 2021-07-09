package io.github.icon02.springbootauthenticationtemplate.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomErrorCode {
    @JsonProperty("cErr") // otherwise would parse to "cerr", no idea why
    private int cErr;
}
