/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2008-2015 VoltDB Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.voltdb.example.springjdbc;

import java.util.List;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringJDBCTemplateTest {

    public static void main(String[] args) throws Exception {

        GenericApplicationContext ctx = new GenericXmlApplicationContext("/applicationContext.xml");
        BeanFactory factory = ctx;
        ContestantDao contestantDao = (ContestantDao) factory.getBean("contestantDao");

        List<ContestantData> list = contestantDao.findContestant("0");
        if (list == null || list.size() == 0) {
            contestantDao.insertContestant("foo", "bar", "0");
        }
        list = contestantDao.findContestant("0");
        if (list.size() == 1) {
            System.out.println("Insert Success");
            ContestantData d = list.get(0);
            if (!d.getCode().equals("0") || !d.getFirstName().equals("foo") || !d.getLastName().equals("bar")) {
                System.out.println("Insert failed");
            }
        }
        contestantDao.updateContestant("foo2", "bar2", "0");
        list = contestantDao.findContestant("0");
        if (list.size() == 1) {
            System.out.println("Update Success");
            ContestantData d = list.get(0);
            if (!d.getCode().equals("0") || !d.getFirstName().equals("foo2") || !d.getLastName().equals("bar2")) {
                System.out.println("Update faild");
            }
        }

        contestantDao.insertContestant(null, null, "1");
        list = contestantDao.getAllContestants();
        if (list.size() == 2) {
            System.out.println("Get All Successful.");
        }

        contestantDao.deleteContestant("1");
        System.out.println("Delete was Successful.");
        contestantDao.insertContestant(null, null, "1");
        contestantDao.deleteAllContestants();
        System.out.println("Delete All was Successful.");
    }
}
