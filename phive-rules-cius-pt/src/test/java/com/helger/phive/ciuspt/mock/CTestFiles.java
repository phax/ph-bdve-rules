/**
 * Copyright (C) 2020-2021 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.phive.ciuspt.mock;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.io.resource.ClassPathResource;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.phive.api.executorset.VESID;
import com.helger.phive.api.executorset.ValidationExecutorSetRegistry;
import com.helger.phive.ciuspt.CIUS_PTValidation;
import com.helger.phive.engine.mock.MockFile;
import com.helger.phive.engine.source.IValidationSourceXML;

@Immutable
public final class CTestFiles
{
  public static final ValidationExecutorSetRegistry <IValidationSourceXML> VES_REGISTRY = new ValidationExecutorSetRegistry <> ();
  static
  {
    CIUS_PTValidation.initCIUS_PT (VES_REGISTRY);
  }

  private CTestFiles ()
  {}

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <MockFile> getAllTestFiles ()
  {
    final ICommonsList <MockFile> ret = new CommonsArrayList <> ();
    for (final VESID aESID : new VESID [] { CIUS_PTValidation.VID_TEAPPS_UBL_CREDITNOTE_200, CIUS_PTValidation.VID_TEAPPS_UBL_INVOICE_200 })
      for (final IReadableResource aRes : getAllMatchingTestFiles (aESID))
        ret.add (MockFile.createGoodCase (aRes, aESID));

    return ret;
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsList <? extends IReadableResource> getAllMatchingTestFiles (@Nonnull final VESID aVESID)
  {
    ValueEnforcer.notNull (aVESID, "VESID");

    if (aVESID.equals (CIUS_PTValidation.VID_TEAPPS_UBL_CREDITNOTE_200))
    {
      return new CommonsArrayList <> (new String [] { "CN_CIUS-PT_syntax-model - NC.xml", },
                                      x -> new ClassPathResource ("/test-files/2.0.0/" + x));
    }
    if (aVESID.equals (CIUS_PTValidation.VID_TEAPPS_UBL_INVOICE_200))
    {
      return new CommonsArrayList <> (new String [] { "I_CIUS-PT_syntax-model - F_b.xml",
                                                      "I_CIUS-PT_syntax-model - F_c.xml",
                                                      "I_CIUS-PT_syntax-model - F_d.xml",
                                                      "I_CIUS-PT_syntax-model - F_e.xml",
                                                      "I_CIUS-PT_syntax-model - F_f.xml",
                                                      "I_CIUS-PT_syntax-model - F_g.xml",
                                                      "I_CIUS-PT_syntax-model - F_h.xml",
                                                      "I_CIUS-PT_syntax-model - F.xml",
                                                      "I_CIUS-PT_syntax-model - ND.xml" },
                                      x -> new ClassPathResource ("/test-files/2.0.0/" + x));
    }

    throw new IllegalArgumentException ("Invalid VESID: " + aVESID);
  }
}
