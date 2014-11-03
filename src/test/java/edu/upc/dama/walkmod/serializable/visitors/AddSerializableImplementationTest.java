package edu.upc.dama.walkmod.serializable.visitors;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.walkmod.javalang.ASTManager;
import org.walkmod.javalang.ast.CompilationUnit;
import org.walkmod.javalang.ast.body.ClassOrInterfaceDeclaration;
import org.walkmod.walkers.VisitorContext;

import edu.upc.dama.walkmod.serializable.visitors.AddSerializableImplementation;

public class AddSerializableImplementationTest {

	@Test
	public void testParsing() throws Exception {
		File f = new File("src/test/resources/sourceBasicExample.txt");
		CompilationUnit cu = ASTManager.parse(f);
		Assert.assertNotNull(cu);
		
		AddSerializableImplementation visitor = new AddSerializableImplementation();
		visitor.visit(cu, new VisitorContext());
		
		String importName = cu.getImports().get(0).getName().getName();
		assertEquals(importName,"java.io.Serializable");
		
		ClassOrInterfaceDeclaration type = (ClassOrInterfaceDeclaration)cu.getTypes().get(0);
		String implementsName = type.getImplements().get(0).getName();
		assertEquals(implementsName,"Serializable");
		
		f = new File("src/test/resources/sourceConflictExample.txt");
		cu = ASTManager.parse(f);
		Assert.assertNotNull(cu);
		
		visitor = new AddSerializableImplementation();
		visitor.visit(cu, new VisitorContext());
		
		int numImports = cu.getImports().size();
		assertEquals(numImports,1);
		
		type = (ClassOrInterfaceDeclaration)cu.getTypes().get(0);
		implementsName = type.getImplements().get(1).getName();
		assertEquals(implementsName,"java.io.Serializable");
		
		System.out.println("Finished");
		
	}
}