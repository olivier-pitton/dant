#include <linux/module.h>	/* Needed by all modules */
#include <linux/kernel.h>	/* Needed for KERN_INFO */
#include <linux/init.h>		/* Needed for the macros */
#include <linux/sched.h>
#include <linux/proc_fs.h>	/* Necessary because we use the proc fs */
#include <asm/uaccess.h>

#define DRIVER_AUTHOR "Olivier Pitton <olivier.pitton@gmail.com>"
#define DRIVER_DESC   "DANT TP"
#define PROCFS_MAX_SIZE 50

static struct proc_dir_entry *entry;
static char data[PROCFS_MAX_SIZE];

static ssize_t spy_write (struct file *file, const char __user *user, size_t size, loff_t * offset);
static int print_process(long pid);

struct file_operations fops = {
	.write = spy_write
};

static int __init start_spy_module(void)
{	
	printk(KERN_INFO "Hello World !\n");
	entry = proc_create("spy", 0777, NULL, &fops);
	if (!entry)
	{
		printk (KERN_INFO "Not created :(\n");
		return -1;
	}
	printk(KERN_INFO "The file has been created under /proc/spy\n");
	return 0;
}

static void __exit end_spy_module(void)
{
	proc_remove(entry);
	printk(KERN_INFO "Goodbye, world\n");
}

static ssize_t spy_write (struct file *file, const char __user *user, size_t size, loff_t * offset)
{
	long pid;
	size_t procsfs_size = size;
	if (procsfs_size > PROCFS_MAX_SIZE)
	{
		procsfs_size = PROCFS_MAX_SIZE;
	}
	if (copy_from_user(data, user, procsfs_size)) 
	{
		return -EFAULT;
	}
	if (kstrtol(data, 10, &pid))
	{
		printk(KERN_INFO "The content send isn't a number\n");
		return -EFAULT;
	}

	printk(KERN_INFO "Fetch pid %ld\n", pid);
	if (print_process(pid))
	{
		return size;
	}
	return size;
}

static int print_process(long pid) 
{
	struct task_struct *process;
	process =  pid_task(find_vpid(pid), PIDTYPE_PID);
	if (!process) 
	{
		printk(KERN_INFO "We can't find the process for the pid %ld\n", pid);
		return -1;
	}
	printk(KERN_INFO "We found the process %ld named %s\n", pid, process->comm);
	printk(KERN_INFO "The nice is %d\n", task_nice(process));
	set_user_nice(process, -20);
	printk(KERN_INFO "The nice is %d\n", task_nice(process));
	return 0;
}

module_init(start_spy_module);
module_exit(end_spy_module);

MODULE_LICENSE("GPL");
MODULE_AUTHOR(DRIVER_AUTHOR);
MODULE_DESCRIPTION(DRIVER_DESC);

