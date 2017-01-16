package com.wnrconsulting.navigateurlfromliteral;

import com.intellij.ide.BrowserUtil;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Max
 * Date: 08.05.13
 * Time: 21:35
 */
public abstract class OneWayPsiFileReferenceBase<T extends PsiElement> extends PsiPolyVariantReferenceBase<T> {
    public static List<LinkRule> linkRules = new ArrayList<>();

    public OneWayPsiFileReferenceBase(T psiElement) {
        super(psiElement, true);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        String cleanFileName = computeStringValue();
        String url = null;
        for (LinkRule linkRule : linkRules) {
            if (cleanFileName.toLowerCase().startsWith(linkRule.getStartsWith().toLowerCase())) {
                url = linkRule.getNavigateTo().replace("{0}", cleanFileName);
                break;
            }
            
        }
        if (url != null) {
            ResolveResult[] result = new ResolveResult[1];
            String finalUrl = url;
            result[0] = new PsiElementResolveResult(new MyNavigatablePsiElement() {
                @Override
                public void navigate(boolean b) {
                    //Not sure thread is needed, is an attempt to avoid an issue where the browser doesn't gain focus when link is clicked
                    new Thread(){
                        @Override
                        public void run() {
                            try {

                                Thread.sleep(1000); //seems to help avoid an issue where the browser doesn't gain focus when link is clicked.
                                BrowserUtil.browse(new URI(finalUrl));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

                @NotNull
                @Override
                public Project getProject() throws PsiInvalidElementAccessException {
                    return getElement().getProject();
                }
            }
            );
            return result;
        }
        return new ResolveResult[0];

    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return EMPTY_ARRAY;
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        return false;
    }

    @NotNull
    protected abstract String computeStringValue();

    private abstract static class MyNavigatablePsiElement implements NavigatablePsiElement {
        @Nullable
        @Override
        public <T> T getUserData(@NotNull Key<T> key) {
            return null;
        }

        @Override
        public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {

        }

        @Override
        public Icon getIcon(@IconFlags int i) {
            return null;
        }

        @Nullable
        @Override
        public String getName() {
            return "N/A";
        }

        @Nullable
        @Override
        public ItemPresentation getPresentation() {
            return null;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public boolean isWritable() {
            return false;
        }

        @Nullable
        @Override
        public PsiReference getReference() {
            return null;
        }

        @NotNull
        @Override
        public PsiReference[] getReferences() {
            return new PsiReference[0];
        }

        @Nullable
        @Override
        public <T> T getCopyableUserData(Key<T> key) {
            return null;
        }

        @Override
        public <T> void putCopyableUserData(Key<T> key, @Nullable T t) {

        }

        @Override
        public boolean processDeclarations(@NotNull PsiScopeProcessor psiScopeProcessor, @NotNull ResolveState resolveState, @Nullable PsiElement psiElement, @NotNull PsiElement psiElement1) {
            return false;
        }

        @Nullable
        @Override
        public PsiElement getContext() {
            return null;
        }

        @Override
        public boolean isPhysical() {
            return false;
        }

        @NotNull
        @Override
        public GlobalSearchScope getResolveScope() {
            return null;
        }

        @NotNull
        @Override
        public SearchScope getUseScope() {
            return GlobalSearchScope.EMPTY_SCOPE;
        }

        @Override
        public ASTNode getNode() {
            return null;
        }

        @Override
        public boolean isEquivalentTo(PsiElement psiElement) {
            return false;
        }

        @Override
        public boolean canNavigateToSource() {
            return true;
        }

        @Override
        public boolean canNavigate() {
            return true;
        }

        @NotNull
        @Override
        public Project getProject() throws PsiInvalidElementAccessException {
            return null;
        }

        @NotNull
        @Override
        public Language getLanguage() {
            return Language.ANY;
        }

        @Override
        public PsiManager getManager() {
            return null;
        }

        @NotNull
        @Override
        public PsiElement[] getChildren() {
            return new PsiElement[0];
        }

        @Override
        public PsiElement getParent() {
            return null;
        }

        @Override
        public PsiElement getFirstChild() {
            return null;
        }

        @Override
        public PsiElement getLastChild() {
            return null;
        }

        @Override
        public PsiElement getNextSibling() {
            return null;
        }

        @Override
        public PsiElement getPrevSibling() {
            return null;
        }

        @Override
        public PsiFile getContainingFile() throws PsiInvalidElementAccessException {
            return null;
        }

        @Override
        public TextRange getTextRange() {
            return null;
        }

        @Override
        public int getStartOffsetInParent() {
            return 0;
        }

        @Override
        public int getTextLength() {
            return 0;
        }

        @Nullable
        @Override
        public PsiElement findElementAt(int i) {
            return null;
        }

        @Nullable
        @Override
        public PsiReference findReferenceAt(int i) {
            return null;
        }

        @Override
        public int getTextOffset() {
            return 0;
        }

        @Override
        public String getText() {
            return "yo this is the getText()";
        }

        @NotNull
        @Override
        public char[] textToCharArray() {
            return new char[0];
        }

        @Override
        public PsiElement getNavigationElement() {
            return this;
        }

        @Override
        public PsiElement getOriginalElement() {
            return null;
        }

        @Override
        public boolean textMatches(@NotNull @NonNls CharSequence charSequence) {
            return false;
        }

        @Override
        public boolean textMatches(@NotNull PsiElement psiElement) {
            return false;
        }

        @Override
        public boolean textContains(char c) {
            return false;
        }

        @Override
        public void accept(@NotNull PsiElementVisitor psiElementVisitor) {

        }

        @Override
        public void acceptChildren(@NotNull PsiElementVisitor psiElementVisitor) {

        }

        @Override
        public PsiElement copy() {
            return null;
        }

        @Override
        public PsiElement add(@NotNull PsiElement psiElement) throws IncorrectOperationException {
            return null;
        }

        @Override
        public PsiElement addBefore(@NotNull PsiElement psiElement, @Nullable PsiElement psiElement1) throws IncorrectOperationException {
            return null;
        }

        @Override
        public PsiElement addAfter(@NotNull PsiElement psiElement, @Nullable PsiElement psiElement1) throws IncorrectOperationException {
            return null;
        }

        @Override
        public void checkAdd(@NotNull PsiElement psiElement) throws IncorrectOperationException {

        }

        @Override
        public PsiElement addRange(PsiElement psiElement, PsiElement psiElement1) throws IncorrectOperationException {
            return null;
        }

        @Override
        public PsiElement addRangeBefore(@NotNull PsiElement psiElement, @NotNull PsiElement psiElement1, PsiElement psiElement2) throws IncorrectOperationException {
            return null;
        }

        @Override
        public PsiElement addRangeAfter(PsiElement psiElement, PsiElement psiElement1, PsiElement psiElement2) throws IncorrectOperationException {
            return null;
        }

        @Override
        public void delete() throws IncorrectOperationException {

        }

        @Override
        public void checkDelete() throws IncorrectOperationException {

        }

        @Override
        public void deleteChildRange(PsiElement psiElement, PsiElement psiElement1) throws IncorrectOperationException {

        }

        @Override
        public PsiElement replace(@NotNull PsiElement psiElement) throws IncorrectOperationException {
            return null;
        }
    }
}
