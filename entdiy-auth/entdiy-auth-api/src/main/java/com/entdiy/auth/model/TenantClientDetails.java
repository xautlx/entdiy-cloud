package com.entdiy.auth.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TenantClientDetails extends BaseClientDetails {


}